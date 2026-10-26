# AutoRent

## Descrição curta do projeto

AutoRent é um sistema de gestão de frotas e locação de veículos desenvolvido em **Java** com **Jakarta EE**, oferecendo interface web responsiva (JSF) e um cliente Java SE para consumo de serviços. O objetivo é simplificar o controle de agências, veículos, acessórios e locações, permitindo a administração completa de usuários, permissões e relatórios.

## Tecnologias usadas

- **Java**: 21 (LTS)
- **Jakarta EE**: 10 (Jakarta Faces 4.0, Jakarta Persistence 3.1, etc.)
- **Servidor de aplicação recomendado**: **WildFly 27** (ou qualquer servidor compatível com Jakarta EE 10)
- **Frontend**: JSF (Facelets), HTML5, CSS3 (arquivo `resources/css/app.css`), Bootstrap Icons
- **Build tool**: Maven 3.9.x
- **Banco de dados**: PostgreSQL 16 (scripts SQL incluídos)

## Como rodar localmente

1. **Banco de dados**
   ```bash
   # Crie o banco de dados e usuário
   createdb autorent
   psql -d autorent -f db/scripts/create-schema.sql
   psql -d autorent -f db/scripts/seed-data.sql
   ```
   > Os scripts `create-schema.sql` e `seed-data.sql` estão na pasta `db/scripts/`.

2. **Configurar o datasource**
   - No `standalone.xml` (ou `standalone-full.xml`) do WildFly, adicione o datasource `java:/PostgresDS` apontando para o banco criado.
   - Atualize o arquivo `src/main/resources/META-INF/persistence.xml` com o JNDI do datasource, se necessário.

3. **Compilar e empacotar**
   ```bash
   mvn clean package
   ```
   Isso gera o EAR `target/AutoRentEAR.ear`.

4. **Deploy no WildFly**
   - Copie o EAR para a pasta `standalone/deployments/` do WildFly.
   - Inicie o servidor (ou reinicie caso já esteja em execução):
   ```bash
   ./standalone.sh -c standalone-full.xml
   ```
   - A aplicação ficará disponível em: `http://localhost:8080/AutoRentWeb`.

## Credenciais padrão para testes

| Usuário | Senha | Papel |
|---------|-------|-------|
| `admin` | `admin123` | Administrador (acesso total) |
| `manager` | `manager123` | Gerente de agência |
| `user` | `user123` | Usuário comum (visualização limitada) |

## Como rodar o client Java SE

1. Navegue até o módulo `AutoRentClient`:
   ```bash
   cd AutoRentClient
   ```
2. Compile o cliente (já incluído no EAR, mas pode ser executado separadamente):
   ```bash
   mvn compile
   ```
3. Execute a aplicação cliente:
   ```bash
   mvn exec:java -Dexec.mainClass="br.com.tainararcs.autorent.client.Main"
   ```
   O cliente abrirá um console que permite autenticar usando as credenciais acima e executar operações de CRUD via JPA.

## Lista de JPQL implementadas e como testá‑las

| Nome da Query | JPQL | Descrição | Como testar |
|---------------|------|-----------|-------------|
| `findAllRentals` | `SELECT r FROM Rental r` | Recupera todas as locações. | Acesse a página **Listar Locação** (`/listrental.xhtml`) ou use o cliente SE com o método `rentalService.findAll()`. |
| `findRentalsByAgency` | `SELECT r FROM Rental r WHERE r.agency.id = :agencyId` | Locações de uma agência específica. | Na UI, selecione a agência no filtro da lista de locações. |
| `findVehiclesAvailable` | `SELECT v FROM Vehicle v WHERE v.status = 'AVAILABLE'` | Veículos disponíveis para locação. | Na página **Listar Veículos**, use o filtro "Disponíveis". |
| `findAgencyByName` | `SELECT a FROM Agency a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))` | Busca agência por nome (case‑insensitive). | No menu **Agências**, utilize a busca por nome. |
| `findAccessoriesByCategory` | `SELECT a FROM Accessory a WHERE a.category = :category` | Acessórios de uma categoria. | Na página **Listar Acessórios**, escolha a categoria no filtro. |
| `findRentalsByUser` | `SELECT r FROM Rental r WHERE r.user.username = :username` | Locações feitas por um usuário. | No cliente SE, chame `rentalService.findByUser(username)`. |
| `findOverdueRentals` | `SELECT r FROM Rental r WHERE r.dueDate < CURRENT_DATE AND r.returned = FALSE` | Locações vencidas ainda não devolvidas. | Na UI, acesse a seção **Relatórios** → *Locações em atraso*. |
| `findTotalRevenue` | `SELECT SUM(r.totalPrice) FROM Rental r WHERE r.startDate BETWEEN :start AND :end` | Receita total em um período. | Use o cliente SE ou a página de **Dashboard** que exibe o total do mês. |

> **Dica:** Todos os endpoints REST (se houver) e os serviços EJB podem ser testados via *Postman* ou diretamente pela interface web.

---
