package com.app.games.utils;

import com.app.games.model.Comics;
import com.app.games.model.QuestionType;
import com.app.games.model.ShortVideo;
import com.app.games.model.User;

import java.io.File;
import java.util.ArrayList;

public class SharedData {


    public static int type;

    public static User user;
    public static Comics comics;
    public static ShortVideo shortVideo;
    public static String veificationId;
    public static User test_user;
    public static ArrayList<OrderProductHelper> cart;
    public static String text;
    public static ArrayList<OrderProductHelper> order_details;
    public static User.MyCar myCar;
    public static int question_section; //1=>comics , 2=>video
    public static Comics.Question comicQuestion;
    public static ShortVideo.Question shortVideoQuestion;
    public static QuestionType question_type;
    public static boolean correct;
    public static int general_question;
    public static int muslim;
    public static File last_file;
}
