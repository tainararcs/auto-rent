# Auto Rent

Auto Rent é um sistema de gestão de frotas e locação de veículos desenvolvido em **Java** com **Jakarta EE**, oferecendo interface web responsiva (JSF) e um cliente Java SE para consumo de serviços. O objetivo é simplificar o controle de agências, veículos, acessórios e locações, permitindo a administração completa de usuários, permissões e relatórios.

## Tecnologias usadas

- **Java**: 21 (LTS)
- **Jakarta EE**: 10 (Jakarta Faces 4.0, Jakarta Persistence 3.1, EJB 4.0)
- **Servidor de Aplicação**: **WildFly 27** (ou superior compatível com Jakarta EE 10)
- **Frontend Web**: JSF (Facelets), CSS3 customizado, Bootstrap Icons
- **Cliente Desktop**: Java SE (com chamadas remotas via EJB Client)
- **Ferramenta de Build**: Maven 3.9.x
- **Banco de Dados**: PostgreSQL 16 (scripts SQL incluídos)

## Como rodar localmente


Siga o passo a passo abaixo para configurar e executar o projeto no seu ambiente de desenvolvimento:

### 1. Clonar o Repositório
```bash
git clone [https://github.com/tainararcs/auto-rent.git](https://github.com/tainararcs/auto-rent.git)
cd auto-rent
```
2. Configurar o Banco de Dados
   1. Crie uma base de dados no PostgreSQL chamada autorent
   ```
   createdb autorent
   ```
   2. Importe o script de dump com a estrutura e os dados iniciais:   
   ```
   -d autorent -f db/autorent_dump.sql
   ```
3. Configurar o Datasource no WildFlyInicie o seu servidor WildFly
- Acesse o painel de administração web do WildFly (geralmente em http://localhost:9990).
- Certifique-se de que o driver do PostgreSQL (postgresql-42.x.x.jar) está instalado.
- Crie um Datasource com o nome JNDI java:/PostgreDS associado à base de dados autorent, configurando corretamente as credenciais de acesso (user e password).
4. Compilar o projeto 
- Atalho na IDE: Ctrl + F11
- cliente abrirá um console que permite autenticar usando as credenciais acima e executar operações de CRUD via JPA.
   
## Credenciais padrão para testes

| Usuário | Senha | Papel |
|---------|-------|-------|
| `admin` | `1111` | Administrador (acesso total) |
| `ana` | `2222` | Usuário comum (visualização limitada) |
| `bruno` | `3333` | Usuário comum (visualização limitada) |


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
