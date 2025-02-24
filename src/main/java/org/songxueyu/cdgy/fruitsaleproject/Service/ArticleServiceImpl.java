package org.songxueyu.cdgy.fruitsaleproject.Service;

import org.songxueyu.cdgy.fruitsaleproject.DTO.ArticleDTO;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.ArticleMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.CommentMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.FollowMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.UserMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.ArticleService;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Article;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FollowMapper followMapper;

    @Override
    public List<ArticleDTO> getAllArticle(QueryInfo queryInfo){
        if (!queryInfo.getQuerytext().equals("")){

            return articleMapper.QueryArticleByTitle(queryInfo.getQuerytext());
        }else{

            return articleMapper.QueryAllArticle();
        }
    }

    @Override
    public String getContent(String article_id){
        return articleMapper.GetContentByID(article_id);
    }

    @Override
    public int addArticle(ArticleDTO article){
        article.setArticle_dto_id(UuidUtil.getUuid());
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date article_time = new Date();
        article.setArticle_dto_time(sdFormat.format(article_time).toString());
        return articleMapper.AddArticle(article);
    }
    @Override
    public  int deleteArticle(String article_id){
        int i = articleMapper.DeleteArticle(article_id);
        int flag=0;
        if (i!=0) flag = commentMapper.deleteCommentByArticle(article_id);
        return i;
    }

    @Override
    public int AddArticleLike(String like_article_id,String like_user_name){
        int star = articleMapper.GetArticleStar(like_article_id);
        star = star +1;
        articleMapper.UpdateArticleStar(star,like_article_id);
        return articleMapper.AddArticleLike(UuidUtil.getUuid(),like_article_id,like_user_name);
    }
    @Override
    public int DeleteArticleLike(String like_article_id,String like_user_name){
        int star = articleMapper.GetArticleStar(like_article_id);
        star = star - 1;
        articleMapper.UpdateArticleStar(star,like_article_id);
        return articleMapper.DeleteArticleLike(like_article_id,like_user_name);
    }

    @Override
    public List<ArticleDTO> GetAllArticleByUser(String article_user_name){
        return articleMapper.GetAllArticleByUser(article_user_name);
    }
    @Override
    public int UpdateArticleClick(String article_id){
        int click = articleMapper.GetArticleClick(article_id);
        click = click + 1;
        return articleMapper.UpdateArticleClick(click,article_id);
    }

    @Override
    public List<ArticleDTO> GetSimilarArticle(String article_id) {
        String title = articleMapper.GetTitleByID(article_id);
        List<ArticleDTO> articleDTOS = articleMapper.GetSimilarArticle(title);
        if (!articleDTOS.isEmpty()) return articleMapper.GetRandomArticle();
        else return articleDTOS;
    }

    /*
     * created by Zhu
     * 热度推荐算法
     * H = (W+I)/(T+1)^G   这是总计算公式
     * W:帖子的热度  T:发帖人的热度   T:发帖时间  G:重力因子  取1.8
     * W是帖子的热度，由用户的点赞、评论、查看加权计算得出。我取的权重分别是5 3 2
     * I是发帖人的热度   由发帖数和粉丝和用户所获得的点赞数加权得出  权重是1 5  4
     *
     * T是发帖到现在一共多少天    G是重力因子 取1.8
     * */
    @Override
    public List<ArticleDTO> GetHotArticle() throws ParseException {
        List<ArticleDTO> articles = articleMapper.QueryAllArticle();
        List<ArticleDTO> temp = articles.stream().collect(Collectors.toList());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;

        for (ArticleDTO article:articles){
            int click=0,reply=0,like=0; //0.1  0.5   0.4
            int user_article_num=0,user_fans=0,user_like=0;//0.1   0.5   0.4

            double W=0, I=0,G=1.8 ,H=0;
            long T=0;
            W=article.getArticle_dto_click()*2 + article.getArticle_dto_reply_num()*5 + article.getArticle_dto_star()*3;
            user_article_num = articleMapper.GetAllArticleByUser(article.getArticle_dto_user_name()).size();
            user_fans = followMapper.GetUserFans(article.getArticle_dto_user_name()).size();
            for (int i=0;i<temp.size();i++){
                if (temp.get(i).getArticle_dto_user_name()==article.getArticle_dto_user_name()) user_like = user_like +1;
            }
            I = user_article_num+ user_fans*5+user_like*4;

            //ParsePosition position= new ParsePosition(0);
            Date date_end = formatter.parse(article.getArticle_dto_time());
            Date date_now = new Date();

            long diff = date_now.getTime() - date_end.getTime();

            T=  diff /nd;
            System.out.println(diff);
            System.out.println(T);
            H=(W+I)/Math.pow(T+1,G);
            article.setArticle_dto_hot(H);
            article.setArticle_dto_user_img(userMapper.GetUserImgByName(article.getArticle_dto_user_name()));
        }

        articles.sort(Comparator.comparing(ArticleDTO::getArticle_dto_hot).reversed());  //最后根据热度进行排序  用户的每一次点赞评论点击查看都会影响到热度
        System.out.println(articles.toString());
        return articles;

    }

    /*
     * 相似帖子推荐算法

    @Override
    public List<Article> GetSimilarArticle(String article_id){
        List<Article> articles = articleMapper.QueryAllArticle();
        List<Integer> list = articleMapper.GetAllArticleId();
        List<Article> articleList = new LinkedList<>();
        try{
            CreatFileUtil.creatFile(articles);
        }catch (Exception e){

        }
        int article_index = list.indexOf(article_id);
        log.info("article_id+"+article_index);
        String recommendArticle = PythonUtils.recommendArticle(article_index);
        log.info("recommendArticle:"+recommendArticle);
        List<String> stringList = Arrays.asList(recommendArticle.split(","));
        int count =0;
        String str=null;
        for (String s: stringList){
            int index = s.indexOf(".");
            if (count==0) str=s.substring(28,index);
            else str=s.substring(27,index);
            count=count++;
            System.out.println(str);
            articleList.add(articleMapper.QueryArticleById(Integer.valueOf(str)));
        }
        for(Article article : articleList){
            article.setArticle_user_img(userMapper.GetUserImgByName(article.getArticle_user_name()));
        }

        return articleList;
    }
        */

}
