package org.songxueyu.cdgy.fruitsaleproject.Service.Interface;

import org.songxueyu.cdgy.fruitsaleproject.DTO.ArticleDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Article;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface ArticleService {
    public List<ArticleDTO> getAllArticle(QueryInfo queryInfo);

    public String getContent(String article_id);

    public int addArticle(ArticleDTO article);

    public int deleteArticle(String article_id);

    public int AddArticleLike(String like_article_id,String like_user_name);

    public int DeleteArticleLike(String like_article_id,String like_user_name);

    public List<ArticleDTO> GetAllArticleByUser(String article_user_name);

    public List<ArticleDTO> GetHotArticle() throws ParseException;

    public int UpdateArticleClick(String article_id);

    public List<ArticleDTO> GetSimilarArticle(String article_id);

}
