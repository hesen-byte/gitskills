package com.hesen.lucene;

import com.hesen.entity.Blog;
import com.hesen.utils.DateUtils;
import com.hesen.utils.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BlogIndex
{
    private Directory dir = null;
    private String lucenePath = "F:\\lucene";


    /**
     * 获得lucene的写出器，首先要配置写出起的属性(主要是分词器),然后指明索引写出的位置。
     * @return
     * @throws Exception
     */
    private IndexWriter getWriter() throws Exception
    {
        dir = FSDirectory.open(Paths.get(lucenePath));
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 增加索引，一个document对应数据库中的一条记录
     * @param blog
     * @throws Exception
     */
    public void addIndex(Blog blog) throws Exception
    {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        //为什么是new Date()
        doc.add(new StringField("releaseDate",
                DateUtils.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        //注意这里使用的是contentNoTag
        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        doc.add(new TextField("keyWord", blog.getKeyWord(), Field.Store.YES));
        writer.addDocument(doc);
        writer.commit();
        writer.close();

    }

    /**
     * 更新索引
     * @param blog
     * @throws Exception
     */
    public void updateIndex(Blog blog) throws Exception
    {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        //为什么是new Date()
        doc.add(new StringField("releaseDate",
                DateUtils.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        //注意这里使用的是contentNoTag
        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        doc.add(new TextField("keyWord", blog.getKeyWord(), Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
        writer.commit();
        writer.close();
    }

    public void deleteIndex(String blogId) throws Exception
    {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term[] {new Term("id", blogId)});
        writer.forceMergeDeletes();
        writer.commit();
        writer.close();
    }

    public List<Blog> searchBlog(String q)
            throws Exception
    {
        this.dir = FSDirectory.open(Paths.get(lucenePath));

        //创建索引读出工具
        IndexReader reader = DirectoryReader.open(this.dir);
        //创建索引搜索工具
        IndexSearcher is = new IndexSearcher(reader);

        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

        /**
         *       创建查询解析器,两个参数：默认要查询的字段的名称，分词器
         *       这里我们按照标题，内容搜索。
         */
        QueryParser parser = new QueryParser("title", analyzer);
        Query query = parser.parse(q);
        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser2.parse(q);

        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);

        TopDocs hits = is.search(booleanQuery.build(), 100);
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        List<Blog> blogList = new LinkedList();

        //将搜索得到的数据封装到blog中，并存入blogList
        for (ScoreDoc scoreDoc : hits.scoreDocs)
        {
            Document doc = is.doc(scoreDoc.doc);
            Blog blog = new Blog();
            blog.setId(Integer.valueOf(Integer.parseInt(doc.get("id"))));
            blog.setReleaseDateStr(doc.get("releaseDate"));
            String title = doc.get("title");
            //搜索的时候不带html标签
            String content = StringEscapeUtils.escapeHtml(doc.get("content"));
            if (title != null)
            {
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if (StringUtils.isEmpty(hTitle)) {
                    blog.setTitle(title);
                } else {
                    blog.setTitle(hTitle);
                }
            }
            if (content != null)
            {
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if (StringUtils.isEmpty(hContent))
                {
                    if (content.length() <= 200) {
                        blog.setContent(content);
                    } else {
                        blog.setContent(content.substring(0, 200));
                    }
                }
                else {
                    blog.setContent(hContent);
                }
            }
            blogList.add(blog);
        }
        return blogList;
    }
}
