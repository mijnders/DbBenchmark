# DbBenchmark
In diesem Projekt beabsichtigen wir, eine umfassende Leistungsanalyse verschiedener **Datenbankmodelle** durchzuführen: **PostgreSQL** als repräsentatives relationales Modell, **MongoDB** als Dokumentenmodell und **Neo4j** als Graphmodell. Unser Ziel ist es, die jeweiligen Stärken und Schwächen der Modelle bei der Verarbeitung von CRUD-Operationen zu ermitteln.

Zu diesem Zweck entwickeln wir eine Reihe von Testszenarien, bei denen wir die CRUD-Methoden (Create, Read, Update, Delete) über die verschiedenen Datenbankmodelle skalieren. Wir beginnen mit einer moderaten Anzahl von Datensätzen und erhöhen die Datenmenge dann exponentiell um eine Zehnerpotenz.

Wir werden die Ausführungszeiten für jede Operation messen und die Skalierbarkeit der Modelle bewerten, indem wir die Veränderungen der Leistung über die verschiedenen Datenbanken hinweg analysieren. Darüber hinaus werden wir versuchen den Ressourcenverbrauch, wie Speicher und CPU-Auslastung, zu dokumentieren, um zu verstehen, wie gut die Datenbankmodelle mit zunehmender Datenmenge skalieren.

Die Ergebnisse dieses Benchmarks werden wertvolle Erkenntnisse liefern und uns helfen, fundierte Entscheidungen darüber zu treffen, welches Datenbankmodell für unsere spezifischen Anforderungen am besten geeignet ist. Darüber hinaus können wir Empfehlungen für andere Projekte geben, die vor ähnlichen Herausforderungen stehen und eine fundierte Entscheidung über das geeignete Datenbankmodell treffen möchten.


## Aufgabenstellung: 
1. Testplanung: Plane die zu benchmarkenden Szenarien und Metriken.
2. Umgebungseinrichtung: Installiere und konfiguriere die Datenbankmodelle und erforderliche Software.
3. Implementierung der Testszenarien: Implementiere den Code für die definierten Szenarien.

4. **Gemeinsam**: Durchführung der Tests: Führe die Tests durch und sammle relevante Daten.


## Testszenarien: 
1. **Daten erstellen**:
    - Bewertung der Leistung der Datenbankmodelle beim Erstellen von Daten.
    - Erhöhe die Anzahl der erstellten Datensätze schrittweise in Zehnerpotenzen.
    - Analysiere die Skalierbarkeit der Datenbankmodelle bei zunehmender Datenmenge.
2. **Daten lesen**:
    - Bewertung der Lesegeschwindigkeit der Datenbankmodelle.
    - Durchführen von Leseabfragen mit einer steigenden Anzahl von Datensätzen.
    - Vergleiche die Ausführungszeiten der Leseoperationen zwischen den Datenbankmodellen.
3. **Daten aktualisieren**:
    - Überprüfung der Geschwindigkeit der Aktualisierung von Daten in den Datenbankmodellen.
    - Durchführen von Aktualisierungsoperationen für eine steigende Anzahl von Datensätzen.
    - Vergleiche die Leistung und Verarbeitungsgeschwindigkeit der Datenbankmodelle.
4. **Daten löschen**:
    - Teste die Geschwindigkeit des Löschens von Daten in den Datenbankmodellen.
    - Führe Löschoperationen für eine steigende Anzahl von Datensätzen durch.
    - Vergleiche die Leistung und Verarbeitungsgeschwindigkeit der Datenbankmodelle.
______________________________________________________________________________________________________________________________________
# Anleitungen zum Aufsetzen einer lokalen Datenbank:

## PostgreSQL:

## MongoDB:

## Neo4j:

1. Lade dir den Installer von Neo4j Desktop [hier](https://neo4j.com/download-center/#desktop) herunter, du musst höchstwahrscheinlich ein Konto anlegen damit du ein Activation Key für die Community Edition erhälst. Sichere dir diesen für später in einer txt-Datei oder ähnlichem.
2. Starte den Installer und folge den angezeigten Schritten um Neo4j zu installieren
3. Melde dich entweder neu an oder füge den Activation Key (aus Schritt 1) in das Feld rechts.
4. Nach der Anmeldung sollte sich Neo4j öffnen. Um dich mit der Oberfläche vertraut zu machen kannst du dir folgende Seite ansehen [Neo4j Desktop Visual Tour](https://neo4j.com/docs/desktop-manual/current/visual-tour/)
5. Wenn du vertraut mit der Oberfläche bist erstelle ein neues Projekt (+ New - Button in der linken oberen Ecke). Dort öffnet sich ein Fenster was dich nach einem Namen deiner DB fragt und nach einem Passwort (kannst für lokale Testszwecke ein einfaches nehmen z.B: "Passwort123")
6. Sobald die Datenbank erstellt wurde klickst du auf die Drei Punkte neben dem Projektnamen und gehst auf Open Folder -> Import. [Wie in diesem Bild](https://neo4j.com/docs/getting-started/_images/generic-open_import_folder.png)
7. In den import Ordner kopierst du jetzt alle CSV Dateien aus diesem Repo.
8. Sobald das erledigt ist drückst du auf den blauen "Open"-Button und der Neo4j Browser sollte sich öffnen.
9. In der Code Zeile im Fenster gibst du folgende Befehle nacheinander ein:
```bash
LOAD CSV WITH HEADERS FROM 'file:///CUSTOMER.csv' AS row
CREATE (:Customer {id: row.ID, firstname: row.FIRSTNAME, lastname: row.LASTNAME, street: row.STREET, city: row.CITY})
```
```bash
LOAD CSV WITH HEADERS FROM 'file:///PRODUCT.csv' AS row
CREATE (:Product {id: row.ID, name: row.NAME, price: toFloat(row.PRICE)})
```
```bash
LOAD CSV WITH HEADERS FROM 'file:///INVOICE.csv' AS row
MATCH (customer:Customer {id: row.CUSTOMERID})
CREATE (invoice:Invoice {id: row.ID, total: toFloat(row.TOTAL)})
CREATE (invoice)-[:BELONGS_TO]->(customer)
```
```bash
LOAD CSV WITH HEADERS FROM 'file:///ITEM.csv' AS row
MATCH (invoice:Invoice {id: row.INVOICEID})
MATCH (product:Product {id: row.PRODUCTID})
CREATE (item:Item {item: row.ITEM, quantity: toInteger(row.QUANTITY), cost: toFloat(row.COST)})
CREATE (item)-[:PART_OF]->(invoice)
CREATE (item)-[:PRODUCT]->(product)
```

Nun sollte dein Neo4j bereit sein zum Benchmarken :)