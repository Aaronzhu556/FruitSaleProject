package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.songxueyu.cdgy.fruitsaleproject.DTO.ArticleDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {


    public List<ArticleDTO> QueryAllArticle();


    public ArticleDTO QueryArticleById(String article_id);


    public List<ArticleDTO> QueryArticleByTitle(String article_title);


    public String GetContentByID(String article_id);

    public int AddArticle(ArticleDTO article);


    public int DeleteArticle(String article_id);

    public int AddArticleLike(String like_id,String like_article_id,String like_user_name);


    public int DeleteArticleLike(String like_article_id,String like_user_name);


    public int GetArticleStar(String article_id);


    public int UpdateArticleStar(int article_star,String article_id);

    public int GetArticleReplyNum(String article_id);


    public int UpdateArticleReplyNum(int article_reply_num,String article_id);


    public List<ArticleDTO> GetAllArticleByUser(String article_user_name);


    public int GetArticleClick(String article_id);


    public int UpdateArticleClick(int article_click,String article_id);

    public List<String>GetAllArticleId();

    public List<ArticleDTO> GetSimilarArticle(String article_title);

    public String GetTitleByID(String article_id);

    public List<ArticleDTO> GetRandomArticle();



}
