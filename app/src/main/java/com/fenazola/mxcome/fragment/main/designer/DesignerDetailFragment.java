package com.fenazola.mxcome.fragment.main.designer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.entry.BaseEntry;
import com.fenazola.mxcome.entry.CommentEntry;
import com.fenazola.mxcome.entry.ShowXMEntry;
import com.fenazola.mxcome.entry.WorkDesignerDetailEntry;
import com.fenazola.mxcome.fragment.common.BrowserDetailFragment;
import com.fenazola.mxcome.fragment.me.CommentDetailFragment;
import com.fenazola.mxcome.fragment.msg.ChatRecyclerFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.taglayout.TagLayout;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.viewpager.AdViewPager;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonWhiteDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2017/3/8.
 */

public class DesignerDetailFragment extends BaseFragment {

    private ImageView top_bg; //工人背景
    private TextView tv_score; //综合等级
    private RatingBar ratingBar; //等级
    private CircleImageView img_photo; //头像
    private TextView work_time; //工作年限
    private TextView now_city; //城市
    private TextView order_cnt; //接单量
    private TextView tv_shield; //装修盾
    private TextView browse_cnt; //浏览量
    private TextView worker_desc; //工人介绍
    private ImageView now_schedu; //立即排班
    private ImageView now_seek; //立即咨询
    private TagLayout client_say_taglayout; //客户印象
    private TextView worker_comment; //好评，中评，差评
    private TextView worker_comment_total; //评论量
    private TextView project_info; //项目信息
    private TextView project_image_cnt; //图片数量
    private FrameLayout project_show;

    private ImageView userPhoto;
    private TextView name;
    private RatingBar ratingBar1;
    private RatingBar ratingBar2;
    private TextView time;
    private TextView tv_comment;

    private int[] mBg = new int[]{
            R.drawable.rect_topic1,
            R.drawable.rect_topic2,
            R.drawable.rect_topic3,
            R.drawable.rect_topic4,
            R.drawable.rect_topic5,
            R.drawable.rect_topic6};
    private TagLayout designer_goodat_style;
    ArrayList<CommentEntry> commentList1 = new ArrayList<CommentEntry>();
    private WorkDesignerDetailEntry entry;
    private RelativeLayout relativeLayout;
    private TextView see_designer_detail;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_designer_detail;
    }

    @Override
    public void initView() {
        super.initView();
        top_bg = (ImageView) findViewById(R.id.top_bg); //工人背景
        tv_score = (TextView) findViewById(R.id.tv_score); //综合等级
        ratingBar = (RatingBar) findViewById(R.id.ratingBar); //等级
        img_photo = (CircleImageView) findViewById(R.id.img_photo); //头像
        work_time = (TextView) findViewById(R.id.work_time); //工作年限
        now_city = (TextView) findViewById(R.id.now_city); //城市
        order_cnt = (TextView) findViewById(R.id.order_cnt); //接单量
        tv_shield = (TextView) findViewById(R.id.tv_shield); //装修盾
        browse_cnt = (TextView) findViewById(R.id.browse_cnt); //浏览量
        worker_desc = (TextView) findViewById(R.id.worker_desc); //工人介绍
        now_schedu = (ImageView) findViewById(R.id.now_schedu); //立即排班
        now_seek = (ImageView) findViewById(R.id.now_seek);
        client_say_taglayout = (TagLayout) findViewById(R.id.client_say_taglayout); //客户印象
        worker_comment = (TextView) findViewById(R.id.worker_comment); //好评，中评，差评
        worker_comment_total = (TextView) findViewById(R.id.worker_comment_total); //评论量
        project_info = (TextView) findViewById(R.id.project_info); //项目信息
        project_image_cnt = (TextView) findViewById(R.id.project_image_cnt); //图片数量
        designer_goodat_style = (TagLayout) findViewById(R.id.designer_goodat_style);//擅长风格
        project_show = (FrameLayout) findViewById(R.id.project_show); //项目展示

        name = (TextView) findViewById(R.id.name);
        ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        time = (TextView) findViewById(R.id.time);
        userPhoto = (ImageView) findViewById(R.id.userPhoto);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        see_designer_detail = (TextView) findViewById(R.id.see_designer_detail);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            entry = (WorkDesignerDetailEntry) args.getSerializable(Constant.key1);
        }
        refreshUI();

        Glide.with(getActivity()).load(Constant.baseUrl + entry.getBase().getPic()).error(R.mipmap.design_house).into(img_photo);

        if (!TextUtils.isEmpty(entry.getBase().getScore())) {
            ratingBar.setRating(Float.parseFloat(entry.getBase().getScore()));
        }
        if (!TextUtils.isEmpty(entry.getBase().getGetorder_l())) {
            order_cnt.setText(entry.getBase().getGetorder_l());
        }
        if (!TextUtils.isEmpty(entry.getBase().getMissto_l())) {
            browse_cnt.setText(entry.getBase().getMissto_l());
        }
        findViewById(R.id.ll_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, CommentDetailFragment.class.getName());
                intent.putParcelableArrayListExtra("comment", commentList1);
                intent.putExtra("base", entry);
                startActivity(intent);
            }
        });

        CommonAdapter<String> goodatStyleAdapter = new CommonAdapter<String>(getContext(), R.layout.tagitem_service) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView text = viewHolder.findViewById(R.id.tag_text);
                text.setBackgroundResource(mBg[position % 6]);
                text.setText(item);
            }
        };
        designer_goodat_style.setAdapter(goodatStyleAdapter);
        if (entry.getBase().getSkill_style() != null) {
            List<String> styleList = entry.getBase().getSkill_style();
            LogUtils.i("style--------", styleList.toString());
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < styleList.size(); i++) {
                String string = Utils.numberToString(Integer.parseInt(styleList.get(i)));
                list.add(string);
            }
            goodatStyleAdapter.addAll(list);
        }

        CommonAdapter clientSayadapter = new CommonAdapter<String>(getContext(), R.layout.tagitem_client_say) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView text = viewHolder.findViewById(R.id.tag_text);
                text.setText(item);
            }
        };
        client_say_taglayout.setAdapter(clientSayadapter);
        if (entry.getBase().getLabel() != null) {
            List<String> datas = new ArrayList<>();
            List<String> label = entry.getBase().getLabel();
            for (int i = 0; i < label.size(); i++) {
                String string = Utils.numberToString(Integer.parseInt(label.get(i)));
                datas.add(string);
            }
            clientSayadapter.addAll(datas);
        }

        now_seek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableTalk talk = Utils.createTalk(getActivity(), entry.getBase().getCid(), entry.getBase().getNick_name(), entry.getBase().getMxcome_no(), entry.getBase().getPic());
                if (talk != null) {
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(Constant.key1, talk);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, ChatRecyclerFragment.class.getName());
                    startActivityForResult(intent, 0x01);
                }
            }
        });

        now_schedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CommonWhiteDialog dialog = new CommonWhiteDialog(getActivity());
                dialog.setMiddleBgRes(R.mipmap.order_designer_bg);
                dialog.setTitle("立即下单");
                dialog.setOnClickConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CommonDialog dialog1 = new CommonDialog(getActivity());
                        dialog1.setTitle("系统已派单，请耐心等待");
                        dialog1.setDisplayMiddleEnable(false);
                        dialog1.setOnClickConfirmListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Message msg = Message.obtain();
                                msg.what = 1;
                                msg.obj = entry;
                                EventBus.getDefault().post(msg);
                                getActivity().setResult(404);
                                getActivity().finish();
                            }
                        });
                        dialog1.setOnClickCancelListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                        dialog1.show();
                    }
                });
                dialog.setOnClickCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });


    }

    public void refreshUI() {
        if (!TextUtils.isEmpty(entry.getComment())) {
            String comment = entry.getComment();
            List<CommentEntry> commentList = GsonUtils.getListFromJSON(comment, CommentEntry.class);
            commentList1 = (ArrayList<CommentEntry>) commentList;
            CommentEntry commentEntry = commentList.get(0);

            if (entry.getTotal_hp() != null && entry.getTotal_zp() != null && commentEntry.getCp() != null) {
                worker_comment.setText(getString(R.string.worker_comment, entry.getTotal_hp(), entry.getTotal_zp(), commentEntry.getCp()));
            } else {
                worker_comment.setText(getString(R.string.worker_comment, "0", "0", "0"));
            }
            if (!TextUtils.isEmpty(entry.getCommentNum())) {
                worker_comment_total.setText(getString(R.string.worker_comment_total, entry.getCommentNum()));
            } else {
                worker_comment_total.setText(getString(R.string.worker_comment_total, "0"));
            }
            if (commentEntry != null) {
                if (commentEntry.getUser_name() != null) {
                    name.setText(commentEntry.getUser_name());
                }
                Glide.with(getActivity()).load(Constant.baseUrl + commentEntry.getPic_url()).error(R.mipmap.me_photo).into(userPhoto);

                if (!TextUtils.isEmpty(commentEntry.getMessage())) {
                    tv_comment.setText(commentEntry.getMessage());
                }
                if (!TextUtils.isEmpty(commentEntry.getService_attitude())) {
                    ratingBar2.setRating(Float.parseFloat(commentEntry.getService_attitude()));
                }
                if (!TextUtils.isEmpty(commentEntry.getWork_quality())) {
                    ratingBar1.setRating(Float.parseFloat(commentEntry.getWork_quality()));
                }
            }
        } else {
            relativeLayout.setVisibility(View.INVISIBLE);
        }

        BaseEntry base = entry.getBase();
        if (base != null) {
            if (!TextUtils.isEmpty(base.getArea_id())) {
                String[] strs = base.getArea_id().split("-");
                for (int i = 0, len = strs.length; i < len; i++) {
                    now_city.setText(strs[1].toString());
                }
            }
            if (!TextUtils.isEmpty(base.getGz_year())) {
                work_time.setText(Html.fromHtml("<font color='#4bb7fd'>" + base.getGz_year() + "</font>" + "年经验"));
            }
        }
        ShowXMEntry show_xm = entry.getShow_xm();
        if (show_xm != null) {
            if (!TextUtils.isEmpty(show_xm.getAddress()) && !TextUtils.isEmpty(show_xm.getCustomer()) && !TextUtils.isEmpty(show_xm.getHouse_str())) {
                project_info.setText(Html.fromHtml(show_xm.getAddress() + "<font color='#FF0000'>" + show_xm.getCustomer() + "</font>|" + show_xm.getHouse_str()));
            } else {
                project_info.setVisibility(View.INVISIBLE);
            }
            if (!TextUtils.isEmpty(show_xm.getGc_pic())) {
                String gc_pic = show_xm.getGc_pic();
                AdViewPager viewPager = new AdViewPager(getActivity());
                final List<String> resIds = new ArrayList<>();
                resIds.add(Constant.imageUrl + gc_pic);
//                resIds.add("http://imgsrc.baidu.com/imgad/pic/item/267f9e2f07082838b5168c32b299a9014c08f1f9.jpg");
//                resIds.add("http://img06.tooopen.com/images/20160712/tooopen_sy_170083325566.jpg");

                viewPager.setUrls(resIds);
                viewPager.setHeight(DPUtils.dip2px(getActivity(), 170));
                project_show.addView(viewPager);
                if (resIds.size() != 0) {
                    project_image_cnt.setText(getString(R.string.worker_project_image_cnt, resIds.size()));
                }
                final int id = viewPager.getId();
                see_designer_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                        intent.putExtra(ZFrameActivity.CLASS_NAME, BrowserDetailFragment.class.getName());
                        intent.putExtra(Constant.key1, id);
                        intent.putStringArrayListExtra(Constant.key2, (ArrayList<String>) resIds);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.black_b));
        toolbar.setTitle(entry.getBase().getNick_name());
    }
}
