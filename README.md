# 📊 Gestão SmarT

> Sistema desktop para controle inteligente de despesas e inventário pessoal.

---

## 📌 Sobre o Projeto

O **Gestão SmarT** é uma solução de software desenvolvida para automatizar e otimizar o controle de despesas e inventário. O objetivo é proporcionar uma interface centralizada e intuitiva para o gerenciamento de compras e o detalhamento de itens adquiridos, permitindo a análise precisa do fluxo de gastos por meio de filtros temporais e visualização gráfica.

---

## 🚀 Funcionalidades

- **Autenticação segura** — Cadastro e login com validação de campos obrigatórios
- **Recuperação de conta** — Redefinição de senha via link enviado ao e-mail cadastrado
- **Dashboard de compras** — Listagem centralizada de todas as compras cadastradas
- **Cadastro de compras** — Registro via Nota Fiscal, descrição e data (com calendário interativo)
- **Gerenciamento de produtos** — Adição e remoção de itens vinculados a cada compra
- **Relatório gráfico** — Filtro por período e faixa de valor, com total consolidado e gráfico de barras
- **Exportação de relatórios** — Download do relatório em formato de arquivo ou encaminhamento para outro software
- **Exclusão em cascata** — Remoção segura de compras sem deixar dados órfãos no banco

---

## 🛠️ Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java |
| Interface | JavaFX |
| Banco de Dados | PostgreSQL (via JDBC) |

---

## 📐 Arquitetura e Fluxo Principal

```
Login / Cadastro
      ↓
Dashboard Geral (listagem de compras)
      ↓              ↓
Nova Compra     Relatório Gráfico
(Nota Fiscal +   (Filtros + Gráfico
 Produtos)        de Barras)
```

---

## 🖥️ Interfaces do Sistema

### Autenticação
Tela de cadastro solicitando Nome, E-mail e Senha, com validação nativa contra campos em branco. Tela de login com opção de redirecionamento para cadastro.

### Dashboard Principal
Exibe todas as compras em uma tabela limpa. Duplo clique em uma linha abre o detalhamento dos produtos da compra. Botão de exclusão com deleção em cascata.

### Cadastro de Nova Compra
Formulário com campos para Nota Fiscal, Descrição Sumária e Data de Compra (seletor de calendário). Após salvar, o usuário é redirecionado para o gerenciamento de itens da compra.

### Gerenciamento de Itens
Grade com os produtos já cadastrados (Nome, Valor Unitário, Tipo/Categoria). Formulário integrado na base da tela para adicionar novos produtos sem sair da janela. Botão para remover item selecionado.

### Relatório Gráfico
Filtros por Data Inicial, Data Final, Valor Mínimo e Valor Máximo. Exibe o **Total no Período** e um gráfico de barras com os gastos agrupados por compra.

---

## 📋 Casos de Uso — Gerar Relatório

**Fluxo principal:**
1. Usuário solicita a geração de um relatório de compras
2. Sistema exibe os filtros disponíveis (período, valor)
3. Usuário seleciona os parâmetros desejados
4. Sistema processa e exibe o relatório
5. Usuário escolhe exportar ou apenas visualizar na tela

**Exceção:** Caso não existam compras para o filtro selecionado, o sistema exibe o alerta *"Nenhuma compra encontrada com os parâmetros desejados"*.

---

## ✅ Critérios de Aceite (BDD)

```gherkin
Cenário 1: Sem compras no período
  DADO QUE o usuário não cadastrou nenhuma compra no último mês
  QUANDO solicitar a geração de um relatório para este período
  ENTÃO é informado que não há compras no último mês

Cenário 2: Sem compras do tipo selecionado
  DADO QUE o usuário não cadastrou nenhuma compra do tipo "livro" no último mês
  QUANDO solicitar a geração de um relatório filtrado por "livros"
  ENTÃO é informado que não há compras do tipo escolhido

Cenário 3: Relatório com resultados
  DADO QUE o usuário fez 3 compras do tipo "livro" de R$ 20,00 cada
  QUANDO solicitar a geração de um relatório para este período
  ENTÃO são exibidos o total, os itens comprados, os valores individuais e as datas
```

---

## ▶️ Como Executar

### Pré-requisitos

- Java 17+
- PostgreSQL rodando localmente
- JavaFX SDK configurado no classpath

### Passos

```bash
# 1. Clone o repositório
git clone https://github.com/Km4rcos/GestaoSmarT.git
cd GestaoSmarT

# 2. Configure o banco de dados
# Crie um banco PostgreSQL e atualize as credenciais no arquivo de configuração

# 3. Compile e execute
# Via IDE (IntelliJ / Eclipse) com o módulo JavaFX configurado
# ou via linha de comando com o classpath do JavaFX
```

> ⚠️ Certifique-se de configurar corretamente as variáveis de conexão com o banco (host, porta, usuário e senha) antes de executar.

---

## 📁 Estrutura do Projeto

```
GestaoSmarT/
├── src/
│   ├── controller/     # Controladores JavaFX (FXML)
│   ├── model/          # Entidades e lógica de negócio
│   ├── dao/            # Acesso ao banco de dados (JDBC)
│   └── view/           # Arquivos FXML das telas
├── resources/
│   └── fxml/           # Layouts das interfaces
└── README.md
```

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Siga os passos:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/minha-feature`)
3. Commit suas alterações (`git commit -m 'feat: adiciona minha feature'`)
4. Push para a branch (`git push origin feature/minha-feature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
