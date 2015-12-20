package com.victor.framework.common.tools;

import java.util.UUID;

public class UUIDTools {
	/**
	 * 生成10位UUID
	 * 
	 * @return
	 */
	public static String getID() {
		UUID uuid = UUID.randomUUID();

		// 改变uuid的生成规则
		return HashTools.convertToHashStr(uuid.getMostSignificantBits(), 5)
				+ HashTools.convertToHashStr(uuid.getLeastSignificantBits(), 5);
	}

	/**
	 * 转换目前32位UUID为10位UUID
	 * 
	 * @param uuidStr
	 * @return
	 */
	public static String convertID(String uuidStr) {
		UUID uuid = UUID.fromString(uuidStr);
		// 改变uuid的生成规则
		return HashTools.convertToHashStr(uuid.getMostSignificantBits(), 5)
				+ HashTools.convertToHashStr(uuid.getLeastSignificantBits(), 5);
	}

	public static void main(String[] args) {
		System.out.println(getID());
		System.out.println(getID());
		System.out.println(getID());
	}
}
