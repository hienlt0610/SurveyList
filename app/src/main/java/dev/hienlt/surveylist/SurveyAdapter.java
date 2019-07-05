package dev.hienlt.surveylist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class SurveyAdapter extends BaseAdapter {
    private static final String TAG = "SurveyAdapter";
    private List<Question> questions;
    private Context context;
    private LayoutInflater inflater;


    public SurveyAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return questions.get(i).hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_survey, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //Update holder view position
        holder.edtAnswer.setTag(position);
        holder.llAnswer.setTag(position);

        Question question = questions.get(position);
        holder.tvTitle.setText(String.format("%s %s", question.getQuestionId(), question.getQuestionContent()));

        if (question.getQuestionType() == Question.TYPE_INPUT) {
            //Type input answer
            holder.edtAnswer.setVisibility(View.VISIBLE);
            holder.llAnswer.setVisibility(View.GONE);
            //Set value
            holder.edtAnswer.removeTextChangedListener(holder);
            holder.edtAnswer.setText(question.getValue());
            holder.edtAnswer.addTextChangedListener(holder);
        } else {
            //Type select answer
            holder.edtAnswer.setVisibility(View.GONE);
            holder.llAnswer.setVisibility(View.VISIBLE);
            addCheckBox(holder, question);
        }

        return view;
    }

    private void addCheckBox(ViewHolder viewHolder, Question question) {
        viewHolder.llAnswer.removeAllViews();
        List<Answer> answers = question.getAnswers();
        String[] resultAnswer;
        //Lấy danh sách những đáp án đã chọn (Tách dựa vào dấu ,)
        if (question.getValue() != null) {
            resultAnswer = question.getValue().split(",");
        } else {
            resultAnswer = new String[0];
        }
        for (Answer answer : answers) {
            CheckBox chkAnswer = new CheckBox(context);
            chkAnswer.setId(View.generateViewId());
            chkAnswer.setText(answer.getAnswer());
            //Duyệt qua tất cả đáp án đã chọn
            for (int i = 0; i < resultAnswer.length; i++) {
                String result = resultAnswer[i];
                //Kiểm tra xem đáp án này đã đc chọn trước đó chưa, nếu rồi thì check
                if (result != null && result.equals(answer.getAnswer())) {
                    chkAnswer.setChecked(true);
                    break;
                }
            }
            chkAnswer.setOnCheckedChangeListener(viewHolder);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            viewHolder.llAnswer.addView(chkAnswer, params);
        }
    }

    class ViewHolder implements TextWatcher, CheckBox.OnCheckedChangeListener {
        TextView tvTitle;
        EditText edtAnswer;
        LinearLayout llAnswer;

        public ViewHolder(View view) {
            tvTitle = view.findViewById(R.id.tv_title);
            edtAnswer = view.findViewById(R.id.edt_answer);
            llAnswer = view.findViewById(R.id.ll_answer);
            edtAnswer.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int position = (int) edtAnswer.getTag();
            //Khi nhập bất kỳ text vào thì set vào model câu hỏi
            questions.get(position).setValue(edtAnswer.getText().toString().trim());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            int position = (int) llAnswer.getTag();
            Question question = questions.get(position);
            //Sau khi check 1 checkbox thì set những đáp án đã chọn vào model câu hỏi
            question.setValue(getSelectedValue(question));
        }

        //Lấy những đáp án đã chọn (có thể chọn nhiều)
        //Vd trả về 1, 2, 3,5
        private String getSelectedValue(Question question) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < llAnswer.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) llAnswer.getChildAt(i);
                if (checkBox.isChecked()) {
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(question.getAnswers().get(i).getAnswer());
                }
            }
            return stringBuilder.toString();
        }
    }
}
