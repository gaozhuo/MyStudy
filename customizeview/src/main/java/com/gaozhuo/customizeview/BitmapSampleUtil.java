package com.gaozhuo.customizeview;

import java.util.Random;

public class BitmapSampleUtil {

	public static String[] IMAGES = new String[] {
			"http://pic35.nipic.com/20131120/8702627_155644386000_2.jpg",
			"http://pic3.nipic.com/20090609/1948687_085606004_2.jpg",
			"http://pic22.nipic.com/20120620/5944083_093920685198_2.jpg",
			"http://d.hiphotos.baidu.com/baike/pic/item/10dfa9ec8a13632761bfd17f938fa0ec08fac72f.jpg",
			"http://hiphotos.baidu.com/michelereiz/pic/item/ae09c23263509376ac4b5fce.jpg",
			"http://img5.duitang.com/uploads/item/201504/07/20150407H0845_ux8CA.jpeg",
			"http://img4q.duitang.com/uploads/blog/201407/31/20140731171646_En2xM.jpeg",
			"http://s6.mogujie.cn/b7/pic/120526/41i1o_kqyu2tcdozbhswlwgfjeg5sckzsew_1118x1600.jpg",
			"http://c.hiphotos.baidu.com/baike/pic/item/b7003af33a87e95027558a8510385343faf2b49c.jpg"
	};

	/**
	 * 随机产生的一个图片Url
	 * 
	 * @return
	 */
	public static String getImageUrl() {
		int index = new Random().nextInt(IMAGES.length);
		return IMAGES[index];
	}
}
