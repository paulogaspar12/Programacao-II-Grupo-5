# Projeto Java — Programacao II - Grupo 5

Este repositório contém uma aplicação Java simples organizada com a seguinte estrutura:

- `src/` — código-fonte Java (ex.: `src/app/App.java`, `src/app/model`...)
- `bin/` — saída de compilação (opcional)
- `lib/` — bibliotecas externas (opcional)

Este README explica como configurar, compilar e executar o projeto em ambientes Windows (PowerShell), Linux/macOS (bash), e também com ferramentas comuns (Maven/Gradle/IDE).

**Pré-requisitos**

- JDK 11 ou superior instalado (recomendado JDK 17+).
- `JAVA_HOME` apontando para a instalação do JDK e `java`/`javac` no `PATH`.
- (Opcional) Maven ou Gradle, se desejar usar um desses build tools.

Verifique a versão do Java:

Windows (PowerShell):

```powershell
java -version
javac -version
```

Linux/macOS (bash):

```bash
java -version
javac -version
```

**Compilar e executar (sem build tool)**

1) Do diretório raiz do projeto, crie a pasta de saída (bin):

Windows (PowerShell):

```powershell
if (-not (Test-Path -Path bin)) { New-Item -ItemType Directory -Path bin }
```

Linux/macOS (bash):

```bash
mkdir -p bin
```

2) Compile todos os arquivos `.java` dentro de `src/` para `bin/`.

Windows (PowerShell):

```powershell
$files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d bin $files
```

Linux/macOS (bash):

```bash
find src -name "*.java" > sources.txt
javac -d bin @sources.txt
rm sources.txt
```

3) Execute a aplicação apontando para a classe principal.
   - Pelo layout de pastas atual a classe principal provável é `app.App`. Ajuste conforme o `package` declarado no seu `App.java`.

Windows (PowerShell):

```powershell
java -cp bin app.App
```

Linux/macOS (bash):

```bash
java -cp bin app.App
```

Se a classe `App` estiver em outro pacote, substitua `app.App` pelo nome completo do pacote.

**Gerar um JAR executável**

1) Depois de compilar em `bin/`, crie um JAR com ponto de entrada:

```bash
jar cfe app.jar <NomeDaClassePrincipal> -C bin .
# Exemplo: jar cfe app.jar app.App -C bin .
```

2) Execute o JAR:

```bash
java -jar app.jar
```

**Usando Maven**

Se decidir migrar para Maven, crie um `pom.xml` na raiz e organize fontes em `src/main/java` e recursos em `src/main/resources`.

Com Maven instalado:

```bash
mvn compile
mvn package    # gera target/*.jar
mvn exec:java -Dexec.mainClass="app.App"  # requer plugin exec
```

**Usando Gradle**

Com um `build.gradle` configurado (Fonte padrão: `src/main/java`):

```bash
./gradlew build
./gradlew run   # caso tenha a task application configurada
```

**Abrir em uma IDE (recomendado)**

- Visual Studio Code: instale a extensão "Extension Pack for Java" (Language Support, Debugger, Maven/Gradle support). Abra a pasta raiz do projeto e importe como projeto Java.
- IntelliJ IDEA / Eclipse: importe o diretório do projeto como projeto Java ou como Maven/Gradle se optar por um desses build tools.

**Estrutura esperada / pontos de atenção**

- Ajuste o comando `java -cp bin app.App` conforme o `package` real usado em `App.java`.
- Se houver dependências externas, coloque os jars em `lib/` e inclua-os no `-cp` (classpath) ao executar ou use Maven/Gradle para gerenciar dependências.

Exemplo de execução com `lib/` contendo libs:

```bash
java -cp "bin:lib/*" app.App    # macOS/Linux
java -cp "bin;lib/*" app.App    # Windows (PowerShell/cmd)
```

**Contribuição**

- Abra issues descrevendo problemas ou melhorias.
- Envie pull requests com pequenas mudanças isoladas.

**Contato**

- Grupo/Autoria: Programacao II - Grupo 5


# Programacao-II-Grupo-5

Repositório de Prog_II
