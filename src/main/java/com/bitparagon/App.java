package com.bitparagon;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 * App
 *
 */
public class App 
{
    public static void main(String[] args)
            throws IOException, ParseException {

        // Analyzer for tokenizing text
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // Create the index in memory
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);
        addDoc(w, "Lucene in Action", "12345");
        addDoc(w, "Lucene for Dummies", "12346");
        addDoc(w, "Managing Gigabytes", "12347");
        addDoc(w, "The Art of Fighting", "12348");
        w.close();

        // Query
        String term = args.length > 0 ? args[0] : "lucene";

        // Use "title" as the default field to use
        Query query = new QueryParser("title", analyzer).parse(term);

        // Search
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        // Display results
        System.out.println("Found " + hits.length + " hits.");

        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            String item = String.format("%d. %s\t%s",
                    i + 1, d.get("isbn"), d.get("title"));
            System.out.println(item);
        }

    }

    private static void addDoc(IndexWriter w, String title, String isbn)
            throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));

        // A string field is not tokenized
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        w.addDocument(doc);
    }
}
