package com.smartx.bill.mepad.mestore.matadata;

public class IOStreamDatas {

	// 数据接口管理
//	public final static String SERVER_IP = "http://manage.mepad.smartx.com.cn/";
//	public final static String SERVER_URL = "http://manage.mepad.smartx.com.cn/";
	public final static String SERVER_IP = "http://10.10.10.3/";
	public final static String SERVER_URL = "http://10.10.10.3/project/app/cms/";
	public final static String APPSINFO_URL = "index.php?s=/IOStream/AppsInfo/index.html";
	public final static String CATEGORY_URL = "index.php?s=/IOStream/Category/index.html";
	public final static String SPECIAL_URL = "index.php?s=/IOStream/Special/index.html";

	// 推荐位管理
	public final static String POSITION_TOP = "1";
	public final static String POSITION_EXCELLENT = "2";
	public final static String POSITION_NEW = "3";

	// 数据类型管理
	public final static int APP_DATA = 1;
	public final static int CATEGORY_DATA = 2;
	public final static int SPECIAL_DATA = 3;

	// 分类大类ID管理
	public final static String CATEGORY_STUDY = "7";
	public final static String CATEGORY_GAME = "8";
	public final static String CATEGORY_TOOLS = "9";

	// 分类类型管理
	public final static String CATEGORY_TYPE = "0";
	public final static String CATEGORY_APP_TYPE = "1";

	// ViewPagers管理
	public final static int VIEWPAGER_RECOM = 1;
	public final static int VIEWPAGER_HOME = 2;
	public final static int VIEWPAGER_CATE_RECOM = 3;
	public final static int VIEWPAGER_DIALOG = 4;

	// Tabs管理
	public final static int TAB_EXCELLENT = 1;
	public final static int TAB_NEW = 2;
	public final static int TAB_RANKING = 3;

	// DownLoad Information
	public static final String DOWNLOAD_FOLDER_NAME = "MeAppStore";

	// 下拉刷新的页数管理，初始值为0，即抓取0~20的数据
	public static int rankingPages = 0;
	public static int meExcellentPages = 0;
	public static int meNewPages = 0;
	public static int categoryExcellentPages = 0;
	public static int categoryNewPages = 0;
	public static int categoryRankingPages = 0;

	// 下拉刷新的页数管理，初始值为0，即抓取0~20的数据
	public final static int RANKING_GRIDVIEW = 0;
	public final static int ME_EXCELLENT_GRIDVIEW = 1;
	public final static int ME_NEW_GRIDVIEW = 2;
	public final static int CATEGORY_EXCELLENT_GRIDVIEW = 3;
	public final static int CATEGORY_NEW_GRIDVIEW = 4;
	public final static int CATEGORY_RANKING_GRIDVIEW = 5;
}
