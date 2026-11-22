# Auto Rent

<!-- IMAGEM: logo/banner do projeto (opcional). Salve em docs/images/banner.png -->
<p align="center">
  <img src="docs/images/banner.png" alt="Auto Rent" width="600">
</p>

Auto Rent é um sistema de **gestão de frota e locação de veículos** desenvolvido em **Java** com **Jakarta EE**. Ele é composto por:

- uma **aplicação web** (Jakarta Faces/JSF) para clientes e funcionários;
- um **módulo EJB** com as regras de negócio e o acesso a dados via JPA/JPQL;
- um **cliente Java SE** (desktop, via janelas de diálogo) que consome os EJBs remotamente por JNDI.

O sistema permite controlar agências, veículos, acessórios e locações (reserva → retirada → devolução), além de exibir um painel com as métricas globais da frota.

> 📄 O relatório completo do trabalho está em [docs/report.pdf](docs/report.pdf).


## Funcionalidades

| Módulo | Web (xhtml) | Client Java SE |
|--------|:---------:|:--------------:|
| Login/Logout | ✓ | ✓ (somente funcionário) |
| Dashboard com métricas da frota | ✓ | — |
| Cadastro de clientes | — | ✓ |
| Listagem de clientes | — | ✓ |
| Cadastro de agências | - | ✓ |
| Listagem de agências | ✓ | ✓ |
| Cadastro de veículos | — | ✓ |
| Listagem de veículos | ✓ | ✓ |
| Alteração de status do veículo | — | ✓ |
| Cadastro de acessórios | - | ✓ |
| Listagem de acessórios | ✓ | ✓ |
| Realizar locação | ✓ | ✓ |
| Listagem de locações | ✓ | ✓ |
| Locações em andamento | — | ✓ |
| Registrar retirada e devolução | — | ✓ |

**Ciclo de vida da locação:**

```
Cliente/Funcionário realiza locaçao (veículo = RESERVED e locação = RESERVED) -> Funcionário registra retirada (veículo = RENTED e locação = ACTIVE) -> Funcionário registra devolução (veículo = AVAILABLE e locação = COMPLETED)
```

---

## Capturas de tela

<!-- IMAGEM: tela de login → docs/images/web-login.png -->
### Login
![Tela de login](docs/images/web-login.png)

<!-- IMAGEM: dashboard com os cards de métricas → docs/images/web-dashboard.png -->
### Dashboard
![Dashboard](docs/images/web-dashboard.png)

<!-- IMAGEM: lista de veículos com filtros → docs/images/web-list-vehicles.png -->
### Veículos (com filtros)
![Lista de veículos](docs/images/web-list-vehicles.png)

<!-- IMAGEM: formulário de nova locação → docs/images/web-create-rental.png -->
### Nova locação
![Nova locação](docs/images/web-create-rental.png)

<!-- IMAGEM: lista de locações → docs/images/web-list-rentals.png -->
### Locações
![Lista de locações](docs/images/web-list-rentals.png)

<!-- IMAGEM: lista de agências → docs/images/web-list-agencies.png -->
### Agências
![Lista de agências](docs/images/web-list-agencies.png)

<!-- IMAGEM: lista de acessórios → docs/images/web-list-accessories.png -->
### Acessórios
![Lista de acessórios](docs/images/web-list-accessories.png)

<!-- IMAGEM: menu principal do client Java SE → docs/images/desktop-menu.png -->
### Client Java SE
![Menu do client Java SE](docs/images/desktop-menu.png)


## Tecnologias usadas

| Item | Versão |
|------|--------|
| **Java** | 17 (módulos EJB, Web e EAR). O client desktop está configurado no Eclipse para JavaSE‑25, mas roda com Java 17+. |
| **Jakarta EE** | 10 (EAR 10, EJB 4.0, Jakarta Persistence 3.x, Jakarta Faces 4.0, CDI 4.0, Servlet 6.0) |
| **Servidor de aplicação recomendado** | **WildFly 27** (`WildFly 27.0 Runtime`) — ou outro servidor compatível com Jakarta EE 10 |
| **Provedor JPA** | Hibernate ORM 6 (embutido no WildFly) |
| **Banco de dados** | PostgreSQL (testado com a versão 16) + driver JDBC do PostgreSQL |
| **Frontend** | Facelets (XHTML), HTML5, CSS3, JavaScript, Bootstrap Icons |
| **Client Java SE** | `jboss-client.jar` (WildFly naming client) + `InputOutput.jar` (diálogos), ambos em [`AutoRentDesktop/jar/`](AutoRentDesktop/jar/) |
| **IDE / build** | Eclipse IDE for Enterprise Java (projetos Eclipse/WTP, sem Maven) |


## Arquitetura e estrutura do projeto

```
auto-rent/
├── AutoRentClient/    # Biblioteca compartilhada: entidades JPA (model), enums e interfaces remotas (@Remote)
├── AutoRent/          # Módulo EJB: Session Beans + persistence.xml (unidade "autorent")
├── AutoRentWeb/       # Módulo Web: Managed Beans (CDI), AuthFilter e páginas XHTML
├── AutoRentEAR/       # Empacota AutoRent.jar + AutoRentWeb.war (context root /AutoRentWeb)
├── AutoRentDesktop/   # Client Java SE (lookup JNDI remoto dos EJBs)
├── db/
│   └── autorent_dump.sql   # Script de carga inicial (seed)
└── docs/
    ├── report.pdf
    └── images/             # Capturas de tela usadas neste README
```

**Session Beans:**

| Bean | Tipo | Interface remota | Responsabilidade |
|------|------|------------------|------------------|
| `AccessorySB` | `@Stateless` | `IAccessory` | CRUD de acessórios |
| `AgencySB` | `@Stateless` | `IAgency` | CRUD de agências |
| `VehicleSB` | `@Stateless` | `IVehicle` | CRUD e consultas de veículos, alteração de status |
| `RentalSB` | `@Stateless` | `IRental` | Locações, retirada e devolução |
| `UserSB` | `@Stateful` | `IUser`, `IAuth` | CRUD de usuários e sessão do usuário autenticado |
| `FleetSB` | `@Singleton` + `@Startup` | `IFleet` | Métricas globais da frota, compartilhadas entre todos os clientes |


## Como rodar localmente

### Pré-requisitos

- JDK 17 ou superior
- [WildFly 27](https://www.wildfly.org/downloads/)
- PostgreSQL 16 e o [driver JDBC do PostgreSQL](https://jdbc.postgresql.org/download/) (`postgresql-42.x.jar`)
- Eclipse IDE for Enterprise Java and Web Developers (com JBoss Tools, para o runtime WildFly)

### 1. Clonar o repositório

```bash
git clone https://github.com/tainararcs/auto-rent.git
```

No Eclipse: **File → Import → General → Existing Projects into Workspace** e selecione a pasta clonada, marcando os projetos `AutoRent`, `AutoRentClient`, `AutoRentWeb`, `AutoRentEAR` e `AutoRentDesktop`.

### 2. Criar o banco de dados

```bash
psql -U postgres -c "CREATE DATABASE autorent;"
```

> As tabelas **não** estão no script SQL: elas são criadas automaticamente pelo Hibernate (`hibernate.hbm2ddl.auto=update`) no primeiro deploy. Por isso o script de dados (passo 6) só deve ser executado **depois** do deploy.

### 3. Configurar o WildFly

1. Baixe e crie um servidor wodfly pelo Eclipse.
2. No Eclipse, adicione o servidor em **Servers → New → Server → WildFly 27** apontando para a pasta do WildFly.
3. Fazer o deploy do EAR: Adicione projeto AutoRentEAR na tela Add and Remove.
4. Inicie o servidor.
5. Configure o datasource e Confira no log do servidor a mensagem `Deployed "AutoRentEAR.ear"` e as linhas `java:global/AutoRentEAR/AutoRent/...` dos EJBs.


### 4. Configurar o datasource `PostgresDS`

A unidade de persistência usa o JNDI **`java:/PostgresDS`** (veja [`persistence.xml`](AutoRent/ejbModule/META-INF/persistence.xml)).

1. Acesse o console de administração** (`http://localhost:9990`)
2. **Deployments → Upload deployment** e envie o `postgresql-42.x.jar` (isso registra o driver).
3. **Configuration → Subsystems → Datasources & Drivers → Datasources → Add Datasource → PostgreSQL**.
4. Preencha:
   - **Name:** `PostgresDS`
   - **JNDI Name:** `java:/PostgresDS`
   - **Connection URL:** `jdbc:postgresql://localhost:5432/autorent`
   - **Usuário / senha:** os do seu PostgreSQL
5. Clique em **Test Connection** e finalize.


### 5. Carregar os dados iniciais

Com as tabelas já criadas pelo deploy, execute o seed:

```bash
psql -U postgres -d autorent -f db/autorent_dump.sql
```

O script limpa as tabelas (`TRUNCATE ... RESTART IDENTITY CASCADE`) e insere 2 agências, 4 usuários, 3 acessórios, 4 veículos e 2 locações.

> Como o `FleetSB` é um Singleton carregado na inicialização, **reinicie o servidor** (ou clique em atualizar no Dashboard) para que as métricas reflitam os dados inseridos.

### 6. Acessar a aplicação web

```
http://localhost:8080/AutoRentWeb/login.xhtml
```


## Credenciais padrão para testes

O login é feito por **e-mail** e senha.

| E-mail | Senha | Tipo | Permissões |
|--------|-------|------|------------|
| `admin@autorent.com` | `1111` | `EMPLOYEE` | Acesso total na web; único tipo que acessa o client Java SE; vê todas as locações |
| `ana@gmail.com` | `2222` | `CLIENT` | Vê apenas as próprias locações |
| `bruno@gmail.com` | `3333` | `CLIENT` | Vê apenas as próprias locações |
| `carla@gmail.com` | `4444` | `CLIENT` | Vê apenas as próprias locações |

> As senhas são armazenadas como hash simples.


## Como rodar o client Java SE

O client fica no projeto [`AutoRentDesktop`](AutoRentDesktop/) e acessa os EJBs remotamente via `http-remoting://localhost:8080`.

1. Garanta que o WildFly está rodando e que o `AutoRentEAR` foi implantado com sucesso.
2. Confira o build path do `AutoRentDesktop` (**Properties → Java Build Path**):
   - projeto `AutoRent` (e, por consequência, `AutoRentClient`);
   - `jar/jboss-client.jar` e `jar/InputOutput.jar`.
3. Abra `trcs.autorent.gui.AutoRent` ([AutoRent.java](AutoRentDesktop/src/trcs/autorent/gui/AutoRent.java)) e execute com **Run As → Java Application** (`Ctrl + F11`).
4. Faça login com uma conta de **funcionário** (`admin@autorent.com` / `1111`).

Menu do client:

| Menu | Opções |
|------|--------|
| **Cadastrar** | Cliente, Agência, Veículo, Acessório, Realizar Locação |
| **Listar** | Clientes, Agências, Frota da Agência, Locações em Andamento, Acessórios |
| **Atualizar** | Registrar Retirada, Registrar Devolução, Alterar Status do Veículo |

> Se o servidor estiver em outra máquina/porta, altere o `PROVIDER_URL` em [Lookup.java](AutoRentDesktop/src/trcs/autorent/service/Lookup.java) e no arquivo [jndi.properties](AutoRentDesktop/src/jndi.properties).


## Lista de JPQL implementadas e como testá-las

Todas as consultas JPQL do projeto estão nos Session Beans de [`AutoRent/ejbModule/trcs/autorent/sessionbean`](AutoRent/ejbModule/trcs/autorent/sessionbean/). Com `hibernate.show_sql=true`, o SQL gerado por cada uma aparece no console do WildFly, o que facilita a conferência durante os testes.

### Usuários - `UserSB`

| # | Método | JPQL | Descrição | Como testar |
|---|--------|------|-----------|-------------|
| 1 | `listAll()` | `SELECT u FROM User u` | Lista todos os usuários. | Exposto em `IUser`; não é chamado por nenhuma tela atualmente (ver [Limitações](#limitações-conhecidas)). |
| 2 | `getByType(UserType)` | `SELECT u FROM User u WHERE u.type = :type` | Usuários de um tipo (`CLIENT`/`EMPLOYEE`). | **Client SE:** Listar → *Listar Clientes* (usa `CLIENT`). Devem aparecer Ana, Bruno e Carla. |
| 3 | `loginAndReturn(email, senha)` / `login(...)` | `SELECT u FROM User u WHERE u.email = :email AND u.passwordHash = :password` | Autenticação por e-mail e hash da senha. | **Web:** tela de login. **Client SE:** tela inicial. Teste com credencial válida e inválida. |

### Agências - `AgencySB`

| # | Método | JPQL | Descrição | Como testar |
|---|--------|------|-----------|-------------|
| 4 | `listAll()` | `SELECT a FROM Agency a` | Lista todas as agências. | **Web:** menu *Agências* (`listagency.xhtml`) e combos da tela *Nova Locação*. **Client SE:** Listar → *Listar Agências*. |
| 5 | `getAgenciesWithAvailableVehiclesByCategory(String)` | `SELECT DISTINCT v.agency FROM Vehicle v WHERE v.category = :category AND v.status = :status` | Agências que têm veículos **disponíveis** de uma categoria. | Não exposto na interface remota (ver [Limitações](#limitações-conhecidas)). |

### Veículos - `VehicleSB`

| # | Método | JPQL | Descrição | Como testar |
|---|--------|------|-----------|-------------|
| 6 | `listAll()` | `SELECT v FROM Vehicle v JOIN FETCH v.agency` | Lista todos os veículos já com a agência carregada. | **Web:** menu *Veículos* (`listvehicle.xhtml`) sem filtros, ou botão *Limpar Filtros*. |
| 7 | `getByStatus(VehicleStatus)` | `SELECT v FROM Vehicle v JOIN FETCH v.agency WHERE v.status = :status` | Veículos com um status. | **Web:** *Veículos* → filtro *Status* → *Aplicar Filtros*. Também usado na tela *Nova Locação* para listar só os `AVAILABLE`. |
| 8 | `getByCategory(VehicleCategory)` | `SELECT v FROM Vehicle v JOIN FETCH v.agency WHERE v.category = :category` | Veículos de uma categoria. | **Web:** *Veículos* → filtro *Categoria* → *Aplicar Filtros* (ex.: `Utilitário` retorna o Fiat Pulse). |
| 9 | `getByAgency(Agency)` | `SELECT v FROM Vehicle v WHERE v.agency = :agency` | Frota de uma agência. | **Client SE:** Listar → *Consultar Frota da Agência* e informe o ID (1 = Central, 2 = Aeroporto). |
| 10 | `getAvailableVehiclesByAgency(Long)` | `SELECT v FROM Vehicle v JOIN FETCH v.agency a WHERE a.id = :agencyId AND v.status = :status` | Veículos disponíveis em uma agência. | Não exposto na interface remota (ver [Limitações](#limitações-conhecidas)). |
| 11 | `getRentedVehicles()` | `SELECT v FROM Vehicle v JOIN FETCH v.agency WHERE v.status = :status` (`RENTED`) | Veículos atualmente alugados. | Não exposto; o mesmo resultado pode ser obtido na **Web** com o filtro *Status = Alugado* (consulta #7). |

### Locações - `RentalSB`

| # | Método | JPQL | Descrição | Como testar |
|---|--------|------|-----------|-------------|
| 12 | `listAll()` | `SELECT r FROM Rental r JOIN FETCH r.client JOIN FETCH r.vehicle JOIN FETCH r.vehicle.agency JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency` | Todas as locações com cliente, veículo e agências carregados. | **Web:** entre como `admin@autorent.com` e abra *Locações* (`listrental.xhtml`). |
| 13 | `getRentalsByUser(Long)` | `SELECT r FROM Rental r JOIN FETCH r.client JOIN FETCH r.vehicle JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency WHERE r.client.id = :userId` | Locações de um cliente. | **Web:** entre como `ana@gmail.com` (ou `bruno@gmail.com`) e abra *Locações*: só as dela aparecem. |
| 14 | `getActiveRentals()` | `SELECT r FROM Rental r WHERE r.status = :status1 OR r.status = :status2` (`RESERVED`, `ACTIVE`) | Locações em andamento. | **Client SE:** Listar → *Consultar Locações em Andamento*. Use o ID exibido em Atualizar → *Registrar Retirada/Devolução*. |
| 15 | `getByStatus(RentalStatus)` | `SELECT r FROM Rental r WHERE status = :status` | Locações com um status. | Exposto em `IRental`; não é chamado por nenhuma tela atualmente. |
| 16 | `getRentalsByPickupCity(String)` | `SELECT r FROM Rental r JOIN FETCH r.pickupAgency a JOIN FETCH r.client c JOIN FETCH r.vehicle v WHERE a.city = :city` | Locações cuja agência de retirada fica em uma cidade. | Exposto em `IRental`; não é chamado por nenhuma tela atualmente (ex.: `"Juiz de Fora"`). |

### Acessórios - `AccessorySB`

| # | Método | JPQL | Descrição | Como testar |
|---|--------|------|-----------|-------------|
| 17 | `listAll()` | `SELECT a FROM Accessory a` | Lista todos os acessórios. | **Web:** menu *Acessórios* (`listaccessory.xhtml`) e checkboxes da tela *Nova Locação*. **Client SE:** Listar → *Listar Acessórios*. |

### Métricas da frota - `FleetSB` (Singleton)

| # | Método | JPQL | Descrição | Como testar |
|---|--------|------|-----------|-------------|
| 18 | `refresh()` → `getTotalVehicles()` | `SELECT COUNT(v) FROM Vehicle v` | Total de veículos. | **Web:** *Dashboard* (card de total). |
| 19 | `refresh()` → `getTotalAvailable/Rented/Reserved/Maintenance()` | `SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status` (executada 4×) | Total de veículos por status. | **Web:** *Dashboard*. Faça uma locação ou altere o status de um veículo e clique em atualizar para ver os números mudarem. |
| 20 | `refresh()` → `getAvailableByAgency()` | `SELECT v.agency.id, COUNT(v) FROM Vehicle v WHERE v.status = :status GROUP BY v.agency.id` | Disponíveis agrupados por agência (`Map<idAgência, qtd>`). | Exposto em `IFleet` e `FleetMB.availableByAgency`, mas ainda não exibido no Dashboard. |
