package com.walkud.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Tank on 2017/2/24.
 * Description:文件类
 */

public class FileInfo {
    public static final int NOT_FAV = 0; //未收藏
    public static final int FAV = 1; //已收藏
    public static final int TRANSLATE_FINISHED = 0;
    public static final int NOT_TRANSLATE_FINISHED = 1;

    public static final int TYPE_TITLE = 1;//Title
    public static final int TYPE_BT_BEAN_ITEM = 2;//BT种子文件Item
    public static final int TYPE_ITEM = 3;//文件Item

    /**
     * 文件状态
     * 0.正常 1.上传中 2.加密中 3.解密中 4.加密失败 5.解密失败 6.上传暂停
     */
    public static final int FST_NORMAL = 0;
    public static final int FST_UPLOADING = 1;
    public static final int FST_ENCRYPTING = 2;
    public static final int FST_DECODING = 3;
    public static final int FST_ENCRYPT_FAIL = 4;
    public static final int FST_DECODEING_FAIL = 5;
    public static final int FST_UPLOAD_PAUSE = 6;

    //    ft	int	类型（99、文件夹；1、图片；2、视频；3、音频、4、文档、5、其他文件）
//    fc	String	图片/文件夹的内部编号
//    fn	string	文件/文件夹名称
//    fn_bak	string	文件/文件夹名称
//    fp	string	文件/文件夹的父文件夹内部编号，如果是根路径，返回0
//    fps	string	文件的路径（如：盒子\我的\ABC\）
//    fav	int	是否收藏，1.是，0.不是
//    ut	string	最后修改时间(yyyy-mm-dd HH:MM:SS)
//    tr	int	0,传输完成，1.任务传输中
//    fl	long	文件大小（bytes)
//    st	string	分享时间（yyyy-mm-dd HH:MM:SS)
//    key	String	分享编号（上传给服务器用）
//    httpUrl String  该文件在局域网中的http连接

    private int ft; //类型
    private String fc; //图片/文件夹的内部编号
    private String fn; //文件/文件夹名称
    private String fn_bak; //上传或备份时获取的临时文件名
    private String fp; //文件/文件夹的父文件夹内部编号，如果是根路径，返回0
    private String fps; //文件的路径（不含文件名及后缀，即文件所在的目录）
    private int fav; //是否收藏，1.是，0.不是
    private int tr; //是否传输完成  0,传输完成，1.任务传输中
    private String ut; //最后修改时间(yyyy-mm-dd HH:MM:SS)
    private long fl; //文件大小（bytes)
    private String filePath; //文件绝对路径（包含文件名）
    private boolean isSelected; //是否被选中
    private String savePath; //保存文件的路径
    private long lastModifiedTime; //文件最后修改时间
    private String st; //分享时间（yyyy-mm-dd HH:MM:SS)   //共享该文件用户的openid
    private String key; //分享编号（上传给服务器用）
    private long dt;//收藏时间（yyyy-mm-dd HH:MM:SS)
    private long fs; //文件大小（bytes) 只用于收藏夹页面
    private String backupFileDirCode; //备份到文件夹的code
    private String backupFileDirPath; //备份到文件夹的path
    private String httpUrl; //该文件在局域网中的http连接
    private String acid; //图片id
    private int imgViewType; //图片显示的类型
    private String imgTimeInfo; //图片时间信息
    private String imgAddrInfo; //图片地址信息
    private boolean showMoreArrow; //是否显示更多箭头
    private int fst;//文件状态 0.正常 1.上传中 2.加密中 3.解密中 4.加密失败 5.解密失败

    @JSONField(deserialize = false)
    private int itemType = TYPE_ITEM;//Item类型,默认为任务类型
    @JSONField(deserialize = false)
    private int typeNameResId;//类型名称
    @JSONField(deserialize = false)
    private int operationResId;//操作名称

    public int getFt() {
        return ft;
    }

    public void setFt(int ft) {
        this.ft = ft;
    }

    public String getFc() {
        return fc;
    }

    public void setFc(String fc) {
        this.fc = fc;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
        this.setFn_bak(fn);
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp;
    }

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut;
    }

    public long getFl() {
        return fl;
    }

    public void setFl(long fl) {
        this.fl = fl;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFn_bak() {
        return fn_bak;
    }

    public void setFn_bak(String fn_bak) {
        this.fn_bak = fn_bak;
    }

    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
    }

    public String getBackupFileDirCode() {
        return backupFileDirCode;
    }

    public void setBackupFileDirCode(String backupFileDirCode) {
        this.backupFileDirCode = backupFileDirCode;
    }

    public String getBackupFileDirPath() {
        return backupFileDirPath;
    }

    public void setBackupFileDirPath(String backupFileDirPath) {
        this.backupFileDirPath = backupFileDirPath;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public int getImgViewType() {
        return imgViewType;
    }

    public void setImgViewType(int imgViewType) {
        this.imgViewType = imgViewType;
    }

    public String getImgTimeInfo() {
        return imgTimeInfo;
    }

    public void setImgTimeInfo(String imgTimeInfo) {
        this.imgTimeInfo = imgTimeInfo;
    }

    public String getImgAddrInfo() {
        return imgAddrInfo;
    }

    public void setImgAddrInfo(String imgAddrInfo) {
        this.imgAddrInfo = imgAddrInfo;
    }

    public boolean isShowMoreArrow() {
        return showMoreArrow;
    }

    public void setShowMoreArrow(boolean showMoreArrow) {
        this.showMoreArrow = showMoreArrow;
    }

    public int getFst() {
        return fst;
    }

    public void setFst(int fst) {
        this.fst = fst;
    }


    @Override
    public Object clone() {
        FileInfo fileInfo = null;
        try {
            fileInfo = (FileInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "ft=" + ft +
                ", fc='" + fc + '\'' +
                ", fn='" + fn + '\'' +
                ", fn_bak='" + fn_bak + '\'' +
                ", fp='" + fp + '\'' +
                ", fps='" + fps + '\'' +
                ", fav=" + fav +
                ", tr=" + tr +
                ", ut='" + ut + '\'' +
                ", fl=" + fl +
                ", filePath='" + filePath + '\'' +
                ", isSelected=" + isSelected +
                ", savePath='" + savePath + '\'' +
                ", lastModifiedTime=" + lastModifiedTime +
                ", st='" + st + '\'' +
                ", key='" + key + '\'' +
                ", dt=" + dt +
                ", fs=" + fs +
                ", backupFileDirCode='" + backupFileDirCode + '\'' +
                ", backupFileDirPath='" + backupFileDirPath + '\'' +
                ", httpUrl='" + httpUrl + '\'' +
                ", acid='" + acid + '\'' +
                ", imgViewType=" + imgViewType +
                ", imgTimeInfo='" + imgTimeInfo + '\'' +
                ", imgAddrInfo='" + imgAddrInfo + '\'' +
                ", showMoreArrow=" + showMoreArrow +
                ", fst=" + fst +
                ", itemType=" + itemType +
                ", typeNameResId=" + typeNameResId +
                ", operationResId=" + operationResId +
                '}';
    }

    public FileInfo() {
    }

    public FileInfo(int itemType, int typeNameResId, int operationResId) {
        this.itemType = itemType;
        this.typeNameResId = typeNameResId;
        this.operationResId = operationResId;
    }

    public void setOperationResId(int operationResId) {
        this.operationResId = operationResId;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getTypeNameResId() {
        return typeNameResId;
    }

    public void setTypeNameResId(int typeNameResId) {
        this.typeNameResId = typeNameResId;
    }

    public int getOperationResId() {
        return operationResId;
    }
}
