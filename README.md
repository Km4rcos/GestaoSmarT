# Documentação de Software: Gestão SmarT

## Parte 1: O Ideal (Definição do MVP)
Para o Gestão SmarT, o MVP foi planejado para focar estritamente na jornada principal de controle financeiro e detalhamento de compras em ambiente desktop.

### 🎯 O que ESTÁ no escopo do MVP:
1. Tela de Login/Cadastro com controle básico de autenticação segura do usuário no banco de dados.
2. Painel visual unificado (Dashboard) exibindo a listagem contínua e limpa de todas as compras registradas.
3. Formulário para lançamento rápido de novas compras (Nota Fiscal, Descrição e Data).
4. Interface de gerenciamento interno de itens (acessada via clique duplo), permitindo inserir, listar e remover produtos avulsos (Nome, Valor, Categoria) dentro de uma compra específica.
5. Exclusão segura em cascata, garantindo a integridade do banco relacional (ao apagar uma compra, seus itens são removidos automaticamente).
6. Módulo analítico (BI) com geração de gráfico de barras interativo, permitindo filtrar os gastos totais por intervalo de datas e faixas de valor.
7. Ferramenta de exportação de relatório consolidado em formato de texto simples (`.TXT`).
8. Arquitetura de segurança básica via variáveis de ambiente (`.env`) para proteção das credenciais do PostgreSQL.

### 🚫 O que NÃO ESTÁ no MVP:
* Integração com APIs bancárias para importação automática de gastos ou extratos.
* Leitura automatizada de Notas Fiscais via QR Code ou importação de arquivos XML.
* Exportação de relatórios com formatação visual complexa em formato PDF ou geração de planilhas Excel nativas.
* Controle dinâmico de estoque ou armazém (o sistema registra o que foi comprado e os valores, mas não gerencia baixas de consumo físico).
* Sincronização em nuvem multidespositivo, aplicativo mobile ou versão em plataforma web (o escopo é estritamente aplicação Desktop local).
* Criação de diferentes perfis de usuário com hierarquia de permissões (ex: Administrador vs. Leitor).

---

## Parte 2: A Implementação (Guia Visual e Técnico)
Abaixo estão os resultados do MVP construído, demonstrando a aplicação prática do escopo definido na Etapa 1.

### 🛠 Tecnologias Utilizadas
* **Linguagem:** Java.
* **Interface:** JavaFX (Interface responsiva com suporte a Full Screen).
* **Persistência:** PostgreSQL (via JDBC).
* **Arquitetura:** MVC (Model-View-Controller).

### ⚙️ Funcionalidades Técnicas Detalhadas
* **CRUD Completo:** Criação, leitura, atualização e exclusão funcional de compras e produtos.
* **Integridade Referencial:** Exclusão inteligente com deleção em cascata diretamente controlada pelo banco de dados.
* **Configuração Segura:** Credenciais isoladas via arquivo `.env`, garantindo total segurança contra vazamento de senhas no repositório.
* **Interface Industrial:** Design focado em usabilidade profissional, com barra de navegação superior (Navbar) robusta e layout responsivo que não distorce componentes.
