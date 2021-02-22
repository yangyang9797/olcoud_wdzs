package com.ocloud.qjwdzs.wx;

/**
 * 微信请求用户信息 所需实体类
 * Gson 转好像必须要Getter Setter
 */
public class WxEntity {
    /**
     * 获取token
     */
	private String access_token;
	private int expires_in;
	private String refresh_token;
	private String openid;
	private String scope;

    /**
     * 用户信息
     *
     */
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String[] privilege;
    private String unionid;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getScope() {
		return scope;
	}

}
