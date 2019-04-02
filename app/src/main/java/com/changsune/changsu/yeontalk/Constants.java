package com.changsune.changsu.yeontalk;

public final class Constants {

    public static Integer CURRENT_YEAR_PLUS_ONE = 2020;

    public static String LOAD_LIMIT = "30";

    public static String AGE = "세";

    public static String GENDER = "성별";
    public static String BIRTHYEAR = "생년";
    public static String NATION = "국가";
    public static String REGION = "지역";
    public static String NICKNAME = "닉네임";
    public static String IMAGE = "이미지";
    public static String INSTRODUCTION = "자기소개";
    public static String POINT = "포인트";
    public static String GALLERY = "사진첩";
    // Device Id
    public static String DEVICE_ID = "";

    // Chat Type
    public static String CHAT_TYPE = "CHAT";
    public static String IMAGE_TYPE = "IMAGE";
    public static String VIDEO_TYPE = "VIDEO";
    public static String EXIT_TYPE = "EXIT";

    // Chat Status
    public static String BEFORE_COMPRSSING = "BEFORE_COMPRESSING";
    public static String COMPRESSING = "COMPRESSING";
    public static String AFTER_COMPRSSING = "AFTER_COMPRESSING";
    public static String UPLOADING = "UPLOADING";
    public static String UPLOADINGFAIL = "UPLOADINGFAIL";
    public static String UPLOADED = "UPLOADED";
    public static String DELETED_UNSHOWN = "DELETED_UNSHOWN";

    // Base Url
    public static final String BASE_URL = "http://52.79.51.149/yeontalk/";
    public static final String BASE_FILE_URL = "http://52.79.51.149/yeontalk/uploads/";
    public static final String BASE_IP = "52.79.51.149";
    public static final int PORT = 7777;

    // Base Url_PY
    // android : 10.0.2.2
    // Nox : 172.17.100.2
    public static final String BASE_IP_PYTHON = "10.0.2.2";
    public static final int PORT_PYTHON = 65432;


    // SHAREDPREF_KEY_SETTING
    public static String SHAREDPREF_KEY_SETTING = "SHAREDPREF_KEY_SETTING";
    public static String SHAREDPREF_KEY_SETTING_GENDER = "SHAREDPREF_KEY_SETTING_GENDER";
    public static String SHAREDPREF_KEY_SETTING_MAX_AGE = "SHAREDPREF_KEY_SETTING_MAX_AGE";
    public static String SHAREDPREF_KEY_SETTING_MIN_AGE = "SHAREDPREF_KEY_SETTING_MIN_AGE";
    public static String SHAREDPREF_KEY_SETTING_NATION = "SHAREDPREF_KEY_SETTING_NATION";
    public static String SHAREDPREF_KEY_SETTING_REGION = "SHAREDPREF_KEY_SETTING_REGION";


    // SHAREDPREF_KEY_PROFILE
    public static String SHAREDPREF_KEY_PROFILE = "SHAREDPREF_KEY_PROFILE";
    public static String SHAREDPREF_KEY_PROFILE_USER_ID = "SHAREDPREF_KEY_PROFILE_USER_ID";
    public static String SHAREDPREF_KEY_PROFILE_NICKNAME = "SHAREDPREF_KEY_PROFILE_NICKNAME";
    public static String SHAREDPREF_KEY_PROFILE_GENDER = "SHAREDPREF_KEY_PROFILE_GENDER";
    public static String SHAREDPREF_KEY_PROFILE_BIRTHYEAR = "SHAREDPREF_KEY_PROFILE_BIRTHYEAR";
    public static String SHAREDPREF_KEY_PROFILE_NATION = "SHAREDPREF_KEY_PROFILE_NATION";
    public static String SHAREDPREF_KEY_PROFILE_REGION = "SHAREDPREF_KEY_PROFILE_REGION";
    public static String SHAREDPREF_KEY_PROFILE_IMAGE = "SHAREDPREF_KEY_PROFILE_IMAGE";
    public static String SHAREDPREF_KEY_PROFILE_INSTRODUCTION = "SHAREDPREF_KEY_PROFILE_INSTRODUCTION";
    public static String SHAREDPREF_KEY_PROFILE_POINT = "SHAREDPREF_KEY_PROFILE_POINT";

    public static String SHAREDPREF_KEY_IMAGES = "SHAREDPREF_KEY_IMAGES";

    public static String SHAREDPREF_KEY_CHAT_UPLOADING = "SHAREDPREF_KEY_CHAT_UPLOADING";
    public static String SHAREDPREF_KEY_CHAT_UPLOADING_DATA = "SHAREDPREF_KEY_CHAT_UPLOADING_DATA";


    // CHAT_ACTIVITY_BROADCAST_RECEIVER_KEY
    public static String CHAT_ACTIVITY_BROADCAST_RECEIVER_KEY = "CHAT_ACTIVITY_BROADCAST_RECEIVER_KEY";
    public static String CHAT_ACTIVITY_BROADCAST_RECEIVER_USER_ID_KEY = "CHAT_ACTIVITY_BROADCAST_RECEIVER_USER_ID_KEY";
    public static String CHAT_ACTIVITY_BROADCAST_RECEIVER_ROOM_ID_KEY = "CHAT_ACTIVITY_BROADCAST_RECEIVER_ROOM_ID_KEY";
    public static String CHAT_ACTIVITY_BROADCAST_RECEIVER_CHAT_ID_KEY = "CHAT_ACTIVITY_BROADCAST_RECEIVER_CHAT_ID_KEY";
    public static String CHAT_ACTIVITY_BROADCAST_RECEIVER_DATE_KEY = "CHAT_ACTIVITY_BROADCAST_RECEIVER_DATE_KEY";
    public static String CHAT_ACTIVITY_BROADCAST_RECEIVER_MESSAGE_KEY = "CHAT_ACTIVITY_BROADCAST_RECEIVER_MESSAGE_KEY";
    public static String CHAT_ACTIVITY_BROADCAST_RECEIVER_TYPE_KEY = "CHAT_ACTIVITY_BROADCAST_RECEIVER_TYPE_KEY";

    // CHATBOT_ACTIVITY_BROADCAST_RECEIVER_KEY
    public static String CHATBOT_ACTIVITY_BROADCAST_RECEIVER_KEY = "CHATBOT_ACTIVITY_BROADCAST_RECEIVER_KEY";
    public static String CHATBOT_ACTIVITY_BROADCAST_RECEIVER_MESSAGE_KEY = "CHATBOT_ACTIVITY_BROADCAST_RECEIVER_MESSAGE_KEY";


    public static String CHAT_ACTIVITY_BROADCAST_RECEIVER_FOR_CHANGING_VIEW_KEY = "CHAT_ACTIVITY_BROADCAST_RECEIVER_FOR_CHANGING_VIEW_KEY";



//    public static String BIRTHYEAR = "";
//    public static ArrayList<String> BIRTHYEARS = new ArrayList();
//    public static List<ChatRoom> CHAT_ROOM_LIST = new ArrayList();
//
//
//    public static ArrayList<User> FAVORITES_LIST = new ArrayList();
//    public static String GENDER = "";
//    public static ArrayList<String> GENDERS = new ArrayList();
//    public static String IMAGE = "";
//
//    public static String INTRODUCTION = "";
//    public static String LOAD_LIMIT = "10";
//    public static String LOAD_LIMIT_IMAGE = "15";
//    public static String LOGIN_TIME = "";
//    public static ArrayList<Image> MYPROFILE_IMAGE_LIST = new ArrayList();
//    public static ArrayList<User> MYPROFILE_LIST = new ArrayList();
//    public static String NATION = "";
//    public static ArrayList<String> NATIONS = new ArrayList();
//    public static String NICKNAME = "";
//    public static String PAGING_DOWN_LOGIN_TIME = null;
//    public static String PAGING_UP_LOGIN_TIME = null;
//    public static String POINT = null;
//    public static final String PREFS_SETTING_FRIEND_FILE = "setting_friend.xml";
//    public static final String PREFS_SETTING_GENER = "setting_gender";
//    public static final String PREFS_SETTING_GENER_NUM = "setting_gender_num";
//    public static final String PREFS_SETTING_MAX_AGE = "setting_max_age";
//    public static final String PREFS_SETTING_MIN_AGE = "setting_min_age";
//    public static final String PREFS_SETTING_NATION = "setting_nation";
//    public static final String PREFS_SETTING_NATION_NUM = "setting_nation_num";
//    public static final String PREFS_SETTING_REGION = "setting_region";
//    public static final String PREFS_SETTING_REGION_NUM = "setting_region_num";
//    public static String REGION = "";
//    public static ArrayList<String> REGIONS_CHINA = new ArrayList();
//    public static ArrayList<String> REGIONS_JAPEN = new ArrayList();
//    public static ArrayList<String> REGIONS_KOREA = new ArrayList();
//    public static ArrayList<String> REGIONS_USA = new ArrayList();
//    public static ArrayList<String> SETTING_BIRTHYEARS = new ArrayList();
//    public static String SETTING_GENDER = "";
//    public static ArrayList<String> SETTING_GENDERS = new ArrayList();
//    public static Integer SETTING_GENDER_NUM = Integer.valueOf(0);
//    public static String SETTING_MAX_AGE = "";
//    public static String SETTING_MIN_AGE = "";
//    public static String SETTING_NATION = "";
//    public static ArrayList<String> SETTING_NATIONS = new ArrayList();
//    public static Integer SETTING_NATION_NUM = Integer.valueOf(0);
//    public static String SETTING_REGION = "";
//    public static ArrayList<String> SETTING_REGIONS_CHINA = new ArrayList();
//    public static ArrayList<String> SETTING_REGIONS_JAPEN = new ArrayList();
//    public static ArrayList<String> SETTING_REGIONS_KOREA = new ArrayList();
//    public static ArrayList<String> SETTING_REGIONS_USA = new ArrayList();
//    public static Integer SETTING_REGION_NUM = Integer.valueOf(0);
//    public static ArrayList<User> USERS_IMAGE_LIST = new ArrayList();
//    public static ArrayList<User> USERS_LIST = new ArrayList();
    public static String VIDEO_CALL_TYPE = "VIDEO_CALL";

    public static String VOICE_CALL_TYPE = "VOICE_CALL";
    public static String VOICE_MAIL_TYPE = "VOICE_MAIL";
    public static Integer YEAR_PLUS_1 = Integer.valueOf(2019);
}
