package me.ewriter.rxgank.api.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Zubin on 2016/8/16.
 */
public class GankData {

    /**
     * error : false
     * results : [{"_id":"57ad3de2421aa94a00ef3117","createdAt":"2016-08-12T11:09:22.469Z","desc":"Simplenote 开源了 iOS 版本的 App","publishedAt":"2016-08-12T11:39:10.578Z","source":"chrome","type":"iOS","url":"https://github.com/Automattic/simplenote-ios","used":true,"who":"代码家"},{"_id":"57ad3ee0421aa94a077b3552","createdAt":"2016-08-12T11:13:36.231Z","desc":"类似微信和 QQ 的一组头像效果","publishedAt":"2016-08-12T11:39:10.578Z","source":"chrome","type":"iOS","url":"https://github.com/ttmdung203/MDGroupAvatarView","used":true,"who":"代码家"},{"_id":"57ad4023421aa949ef961f4b","createdAt":"2016-08-12T11:18:59.569Z","desc":"8-12","publishedAt":"2016-08-12T11:39:10.578Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f6qsn74e3yj20u011htc6.jpg","used":true,"who":"代码家"},{"_id":"57ad41e4421aa94a077b3553","createdAt":"2016-08-12T11:26:28.497Z","desc":"爱酱小时候，边打球边哭，太可爱了～～","publishedAt":"2016-08-12T11:39:10.578Z","source":"chrome","type":"休息视频","url":"http://weibo.com/p/230444d389e174bf27572c56c38c4f1366137c","used":true,"who":"Allen"},{"_id":"5718ed8067765974f885bf4e","createdAt":"2016-04-21T23:10:56.276Z","desc":"俄罗斯小哥Oleg Cricket，这是他在迪拜某高楼拍的视频，近距离感受下什么叫战斗民族。。","publishedAt":"2016-08-11T12:07:01.963Z","source":"chrome","type":"休息视频","url":"http://weibo.com/p/230444a15ab25498ca6d014542cb3be5a55b4c","used":true,"who":"LHF"},{"_id":"57a9a6ef421aa90b3aac1ed9","createdAt":"2016-08-09T17:48:31.172Z","desc":"安卓音频录制","publishedAt":"2016-08-11T12:07:01.963Z","source":"chrome","type":"Android","url":"https://github.com/adrielcafe/AndroidAudioRecorder","used":true,"who":"蒋朋"},{"_id":"57aa06eb421aa90b3cb7b0c2","createdAt":"2016-08-10T00:38:03.56Z","desc":"瞬间爆炸菜单按钮的iOS版本，新增两种按钮和可拖拽等新特性。","publishedAt":"2016-08-11T12:07:01.963Z","source":"web","type":"iOS","url":"https://github.com/Nightonke/VHBoomMenuButton","used":true,"who":"Weiping Huang"},{"_id":"57aa8f7b421aa90b3aac1ee4","createdAt":"2016-08-10T10:20:43.504Z","desc":"imagepicker(图片选择器)","publishedAt":"2016-08-11T12:07:01.963Z","source":"web","type":"Android","url":"https://github.com/917386389/imagepickerdemo","used":true,"who":"fsuper"},{"_id":"57aae9ef421aa90c2c3ecb17","createdAt":"2016-08-10T16:46:39.678Z","desc":"Android 开发之 App 启动时间统计","publishedAt":"2016-08-11T12:07:01.963Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/c967653a9468","used":true,"who":"单刀土豆"},{"_id":"57aafbae421aa90c1dcbcbb8","createdAt":"2016-08-10T18:02:22.207Z","desc":"Add a headview for any view and supports sticking the navigator on the top when ItemView scrolls.","publishedAt":"2016-08-11T12:07:01.963Z","source":"chrome","type":"Android","url":"https://github.com/w446108264/ScrollableLayout","used":true,"who":"dreamxuwj"},{"_id":"57abee04421aa93faddbfb58","createdAt":"2016-08-11T11:16:20.682Z","desc":"页面切换指示器","publishedAt":"2016-08-11T12:07:01.963Z","source":"chrome","type":"iOS","url":"https://github.com/popwarsweet/PageControls","used":true,"who":"代码家"},{"_id":"57abf032421aa93faddbfb59","createdAt":"2016-08-11T11:25:38.383Z","desc":"盘点那些超棒的 Telegram 机器人","publishedAt":"2016-08-11T12:07:01.963Z","source":"chrome","type":"瞎推荐","url":"https://github.com/DenisIzmaylov/awesome-telegram-bots","used":true,"who":"代码家"},{"_id":"57abf148421aa93faddbfb5a","createdAt":"2016-08-11T11:30:16.863Z","desc":"雅虎开源的一系列 UI 组件，目前只放出了图片选择器组件，做的相当棒，期待雅虎更多的分析。","publishedAt":"2016-08-11T12:07:01.963Z","source":"chrome","type":"iOS","url":"https://github.com/yahoo/YangMingShan","used":true,"who":"代码家"},{"_id":"57abf5ac421aa93fa66e8406","createdAt":"2016-08-11T11:49:00.660Z","desc":"8.11","publishedAt":"2016-08-11T12:07:01.963Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f6pnw6i7lqj20u00u0tbr.jpg","used":true,"who":"代码家"},{"_id":"56efcc4c677659164d5643ca","createdAt":"2016-03-21T18:26:20.122Z","desc":"android 加载动画","publishedAt":"2016-08-10T11:37:13.981Z","source":"chrome","type":"Android","url":"https://github.com/ybq/AndroidSpinKit","used":true,"who":"ybq"},{"_id":"56fce1dd67765933d8be9188","createdAt":"2016-03-31T16:37:49.476Z","desc":"Welcome Coordinator for Android","publishedAt":"2016-08-10T11:37:13.981Z","source":"chrome","type":"Android","url":"https://github.com/txusballesteros/welcome-coordinator","used":true,"who":"Masayuki Suda"},{"_id":"57a9c919421aa90b3aac1edb","createdAt":"2016-08-09T20:14:17.385Z","desc":"Android任意添加贴纸，支持添加Bitmap和Drawable","publishedAt":"2016-08-10T11:37:13.981Z","source":"web","type":"Android","url":"https://github.com/wuapnjie/StickerView","used":true,"who":"FlyingSnowBean"},{"_id":"57a9fa9a421aa90b38e829c6","createdAt":"2016-08-09T23:45:30.933Z","desc":"iOS 中的 block 是如何持有对象的","publishedAt":"2016-08-10T11:37:13.981Z","source":"web","type":"Android","url":"http://draveness.me/block-retain-object/","used":true,"who":"Draveness"},{"_id":"57aa7241421aa90b3aac1edd","createdAt":"2016-08-10T08:16:01.833Z","desc":"漂亮的变换引导效果","publishedAt":"2016-08-10T11:37:13.981Z","source":"chrome","type":"Android","url":"https://github.com/eoinfogarty/Onboarding","used":true,"who":"代码家"},{"_id":"57aa72eb421aa90b3aac1ede","createdAt":"2016-08-10T08:18:51.657Z","desc":"Button 拉长展开效果","publishedAt":"2016-08-10T11:37:13.981Z","source":"chrome","type":"Android","url":"https://github.com/daniel-martins-IR/MagicButton","used":true,"who":"代码家"}]
     */

    @SerializedName("error")
    private boolean error;
    /**
     * _id : 57ad3de2421aa94a00ef3117
     * createdAt : 2016-08-12T11:09:22.469Z
     * desc : Simplenote 开源了 iOS 版本的 App
     * publishedAt : 2016-08-12T11:39:10.578Z
     * source : chrome
     * type : iOS
     * url : https://github.com/Automattic/simplenote-ios
     * used : true
     * who : 代码家
     */

    @SerializedName("results")
    private List<GankItem> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankItem> getResults() {
        return results;
    }

    public void setResults(List<GankItem> results) {
        this.results = results;
    }

}
