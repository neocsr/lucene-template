# Lucene Template

## Dependencies

```
brew install maven
```

## Setup

```
mvn package
```

```
LUCENE_PATH=$HOME/.m2/repository/org/apache/lucene \
LUCENE_PARSER=$LUCENE_PATH/lucene-queryparser/7.4.0/lucene-queryparser-7.4.0.jar \
LUCENE_CORE=$LUCENE_PATH/lucene-core/7.4.0/lucene-core-7.4.0.jar \
java -classpath target/lucene-quickstart-1.0-SNAPSHOT.jar:$LUCENE_PARSER:$LUCENE_CORE \
com.bitparagon.App
```

```
Found 2 hits.
1. 12345	Lucene in Action
2. 12346	Lucene for Dummies
```

Ref.

- http://www.lucenetutorial.com/lucene-in-5-minutes.html

