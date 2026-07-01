# db-connect

app connect multi database

## Tech stack

- Java
- Tauri

<!-- mvn archetype:generate \
  -DgroupId=com.anh \
  -DartifactId=db-engine \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false -->


<!-- npm run tauri dev -->

## Struct

```sh
                    DB Client
                        │
        ┌───────────────┴────────────────┐
        │                                │
        ▼                                ▼
Config Database                  User Database
(lưu setting)                    (database thực)
        │                                │
     SQLite                      PostgreSQL
                                 MySQL
                                 Oracle
                                 SQL Server
                                 SQLite
```
