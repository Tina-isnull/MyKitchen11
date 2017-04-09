package com.example.lcc.mykitchen.utils;

public abstract class ValidateUtil {
	// 5-16位包含大小写字母或数字的用户名校验
	public static final String userNameRegExp = "^[(?=.*[a-z])|(?=.*[A-Z])](?=.*\\d).{4,15}$";
	// 8-16位包含大小写字母或数字的密码校验
	public static final String pwdRegExp = "^[(?=.*\\d)|(?=.*[a-z])|(?=.*[A-Z])].{8,15}$";
	// 邮箱验证
	public static final String emailRegExp = "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";
	// 国内电话号码验证
	public static final String telRegExp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
	// 验证码验证
	public static final String VCodeRegExp = "^[0-9]\\d{5}$";

	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (isNull(str)) {
			return true;
		}
		return "".equals(str);
	}

	public static boolean username(String str) {
		if (!isEmpty(str)) {
			return str.matches(userNameRegExp);
		}
		return false;
	}

	public static boolean password(String str) {
		if (!isEmpty(str)) {
			return str.matches(pwdRegExp);
		}
		return false;
	}

	public static boolean email(String str) {
		if (!isEmpty(str)) {
			return str.matches(emailRegExp);
		}
		return false;
	}

	public static boolean tel(String str) {
		if (!isEmpty(str)) {
			return str.matches(telRegExp);
		}
		return false;
	}

	public static boolean vcode(String str) {
		if (!isEmpty(str)) {
			return str.matches(VCodeRegExp);
		}
		return false;
	}
}