package dev.hienlt.surveylist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
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
        holder.rgAnswer.setTag(position);

        Question question = questions.get(position);
        holder.tvTitle.setText(String.format("%s %s", question.getQuestionId(), question.getQuestionContent()));

        if (question.getQuestionType() == Question.TYPE_INPUT) {
            //Type input answer
            holder.edtAnswer.setVisibility(View.VISIBLE);
            holder.rgAnswer.setVisibility(View.GONE);
            //Set value
            holder.edtAnswer.removeTextChangedListener(holder);
            holder.edtAnswer.setText(question.getValue());
            holder.edtAnswer.addTextChangedListener(holder);
        } else {
            //Type select answer
            holder.edtAnswer.setVisibility(View.GONE);
            holder.rgAnswer.setVisibility(View.VISIBLE);
            addCheckBox(holder, question);
        }

        return view;
    }

    private void addCheckBox(ViewHolder viewHolder, Question question) {
        viewHolder.rgAnswer.removeAllViews();
        List<Answer> answers = question.getAnswers();
        viewHolder.rgAnswer.setOnCheckedChangeListener(null);
        for (Answer answer : answers) {
            RadioButton rbAnswer = new RadioButton(context);
            rbAnswer.setId(View.generateViewId());
            rbAnswer.setText(answer.getAnswer());
            boolean isChecked = question.getValue() != null && question.getValue().equals(answer.getAnswer());
            rbAnswer.setChecked(isChecked);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            viewHolder.rgAnswer.addView(rbAnswer, params);
        }
        viewHolder.rgAnswer.setOnCheckedChangeListener(viewHolder);
    }

    class ViewHolder implements TextWatcher, RadioGroup.OnCheckedChangeListener {
        TextView tvTitle;
        EditText edtAnswer;
        RadioGroup rgAnswer;

        public ViewHolder(View view) {
            tvTitle = view.findViewById(R.id.tv_title);
            edtAnswer = view.findViewById(R.id.edt_answer);
            rgAnswer = view.findViewById(R.id.rg_answer);
            edtAnswer.addTextChangedListener(this);
            rgAnswer.setOnCheckedChangeListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int position = (int) edtAnswer.getTag();
            questions.get(position).setValue(edtAnswer.getText().toString().trim());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            int position = (int) edtAnswer.getTag();
            Question question = questions.get(position);
            List<Answer> answers = question.getAnswers();
            int indexAnser = rgAnswer.indexOfChild(rgAnswer.findViewById(rgAnswer.getCheckedRadioButtonId()));
            Answer selectedAnswer = answers.get(indexAnser);
            question.setValue(selectedAnswer.getAnswer());
        }
    }
}
