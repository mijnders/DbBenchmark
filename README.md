# DbBenchmark
In diesem Projekt beabsichtigen wir, eine umfassende Leistungsanalyse verschiedener Datenbankmodelle durchzuführen: PostgreSQL als repräsentatives relationales Modell, MongoDB als Dokumentenmodell, Redis als Schlüssel-Wert-Modell und Neo4j als Graphmodell. Unser Ziel ist es, die jeweiligen Stärken und Schwächen der Modelle bei der Verarbeitung von CRUD-Operationen zu ermitteln.

Zu diesem Zweck entwickeln wir eine Reihe von Testszenarien, bei denen wir die CRUD-Methoden (Create, Read, Update, Delete) über die verschiedenen Datenbankmodelle skalieren. Wir beginnen mit einer moderaten Anzahl von Datensätzen und erhöhen die Datenmenge dann exponentiell um eine Zehnerpotenz.

Wir werden die Ausführungszeiten für jede Operation messen und die Skalierbarkeit der Modelle bewerten, indem wir die Veränderungen der Leistung über die verschiedenen Datenbanken hinweg analysieren. Darüber hinaus werden wir den Ressourcenverbrauch, wie Speicher und CPU-Auslastung, beobachten, um zu verstehen, wie gut die Datenbankmodelle mit zunehmender Datenmenge skalieren.

Die Ergebnisse dieses Benchmarks werden wertvolle Erkenntnisse liefern und uns helfen, fundierte Entscheidungen darüber zu treffen, welches Datenbankmodell für unsere spezifischen Anforderungen am besten geeignet ist. Darüber hinaus können wir Empfehlungen für andere Projekte geben, die vor ähnlichen Herausforderungen stehen und eine fundierte Entscheidung über das geeignete Datenbankmodell treffen möchten.


Aufgabenstellung: 
1. Experte in der Datenbank werden
2. Lokal installieren der Datenbank und Installation Dokumentieren
3. Vorgefertigte Test Daten bespielen


Testszenarien: 
        alles asynchron
C. 1 -> 10 -> 100 -> 1000 -> 10000
R. 1 -> 10 -> 100 -> 1000 -> 10000
U. 1 -> 10 -> 100 -> 1000 -> 10000
D. 1 -> 10 -> 100 -> 1000 -> 10000

______________________________________________________________________________________________________________________________________
Anleitungen zum Ausetzen einer lokalen Datenbank:

PostgreSQL:

MongoDB:

Redis:

Neo4j: