# Auto Rent

Auto Rent é um sistema de gestão de frotas e locação de veículos desenvolvido em **Java** com **Jakarta EE**, oferecendo interface web responsiva (JSF) e um cliente Java SE para consumo de serviços. O objetivo é simplificar o controle de agências, veículos, acessórios e locações, permitindo a administração completa de usuários, permissões e relatórios.

## Tecnologias usadas

- **Java**: 21 (LTS)
- **Jakarta EE**: 10 (Jakarta Faces 4.0, Jakarta Persistence 3.1, etc.)
- **Servidor de aplicação recomendado**: **WildFly 27** (ou qualquer servidor compatível com Jakarta EE 10)
- **Frontend**: JSF (Facelets), HTML5, CSS3 (arquivo `resources/css/app.css`), Bootstrap Icons
- **Build tool**: Maven 3.9.x
- **Banco de dados**: PostgreSQL 16 (scripts SQL incluídos)

## Como rodar localmente

1. **Clonar o repositório**
   `git clone `

2. Crie e inicie o servidor
   - Baixe o Widfly
   - Adicione o AutoRentEAR ao servidor
   - Inicie-o

3. **Banco de dados**
   - Crie o banco de dados e usuário
   ```bash
   createdb autorent
   psql -d autorent -f db/scripts/autorent_dump.sql
   ```

4. **Configurar o datasource**
   - Em `http://localhost:9900` (interface gráfica de configuração do WildFly), crie o datasource PostgreDS e configure a conexão com o DB.

5. **Executar o projeto**
   - Clone o repositório `git clone `
   - Inicie o servidor (ou reinicie caso já esteja em execução).
   - Acesse: `http://localhost:8080/AutoRentWeb`.

6. Execute a aplicação o client Java SE:
   ```bash
   CTRL + F11
   ```
   O cliente abrirá um console que permite autenticar usando as credenciais acima e executar operações de CRUD via JPA.
   
## Credenciais padrão para testes

| Usuário | Senha | Papel |
|---------|-------|-------|
| `admin` | `1111` | Administrador (acesso total) |
| `ana` | `2222` | Usuário comum (visualização limitada) |
| `ben` | `3333` | Usuário comum (visualização limitada) |


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

---
