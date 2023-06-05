# DbBenchmark
In diesem Projekt beabsichtigen wir, eine umfassende Leistungsanalyse verschiedener Datenbankmodelle durchzuführen: PostgreSQL als repräsentatives relationales Modell, MongoDB als Dokumentenmodell, Redis als Schlüssel-Wert-Modell und Neo4j als Graphmodell. Unser Ziel ist es, die jeweiligen Stärken und Schwächen der Modelle bei der Verarbeitung von CRUD-Operationen zu ermitteln.

Zu diesem Zweck entwickeln wir eine Reihe von Testszenarien, bei denen wir die CRUD-Methoden (Create, Read, Update, Delete) über die verschiedenen Datenbankmodelle skalieren. Wir beginnen mit einer moderaten Anzahl von Datensätzen und erhöhen die Datenmenge dann exponentiell um eine Zehnerpotenz.

Wir werden die Ausführungszeiten für jede Operation messen und die Skalierbarkeit der Modelle bewerten, indem wir die Veränderungen der Leistung über die verschiedenen Datenbanken hinweg analysieren. Darüber hinaus werden wir den Ressourcenverbrauch, wie Speicher und CPU-Auslastung, beobachten, um zu verstehen, wie gut die Datenbankmodelle mit zunehmender Datenmenge skalieren.

Die Ergebnisse dieses Benchmarks werden wertvolle Erkenntnisse liefern und uns helfen, fundierte Entscheidungen darüber zu treffen, welches Datenbankmodell für unsere spezifischen Anforderungen am besten geeignet ist. Darüber hinaus können wir Empfehlungen für andere Projekte geben, die vor ähnlichen Herausforderungen stehen und eine fundierte Entscheidung über das geeignete Datenbankmodell treffen möchten.


# Aufgabenstellung: 
1. Testplanung: Plane die zu benchmarkenden Szenarien und Metriken.
2. Umgebungseinrichtung: Installiere und konfiguriere die Datenbankmodelle und erforderliche Software.
3. Implementierung der Testszenarien: Implementiere den Code für die definierten Szenarien.

4. Gemeinsam: Durchführung der Tests: Führe die Tests durch und sammle relevante Daten.


# Testszenarien: 
1. Daten erstellen:
    - Bewertung der Leistung der Datenbankmodelle beim Erstellen von Daten.
    - Erhöhe die Anzahl der erstellten Datensätze schrittweise in Zehnerpotenzen.
    - Analysiere die Skalierbarkeit der Datenbankmodelle bei zunehmender Datenmenge.
2. Daten lesen:
    - Bewertung der Lesegeschwindigkeit der Datenbankmodelle.
    - Durchführen von Leseabfragen mit einer steigenden Anzahl von Datensätzen.
    - Vergleiche die Ausführungszeiten der Leseoperationen zwischen den Datenbankmodellen.
3. Daten aktualisieren:
    - Überprüfung der Geschwindigkeit der Aktualisierung von Daten in den Datenbankmodellen.
    - Durchführen von Aktualisierungsoperationen für eine steigende Anzahl von Datensätzen.
    - Vergleiche die Leistung und Verarbeitungsgeschwindigkeit der Datenbankmodelle.
4. Daten löschen:
    - Teste die Geschwindigkeit des Löschens von Daten in den Datenbankmodellen.
    - Führe Löschoperationen für eine steigende Anzahl von Datensätzen durch.
    - Vergleiche die Leistung und Verarbeitungsgeschwindigkeit der Datenbankmodelle.
______________________________________________________________________________________________________________________________________
# Anleitungen zum Aufsetzen einer lokalen Datenbank:

PostgreSQL:

MongoDB:

Redis:

Neo4j: