package com.example.codelingo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(
    private val questions: MutableList<Question>, // Mude para MutableList
    private val onAnswerSelected: (Question, Boolean) -> Unit
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)
        val btnOption1: Button = itemView.findViewById(R.id.btnOption1)
        val btnOption2: Button = itemView.findViewById(R.id.btnOption2)
        val btnOption3: Button = itemView.findViewById(R.id.btnOption3)
        val btnOption4: Button = itemView.findViewById(R.id.btnOption4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("QuestionAdapter", "Item count: ${questions.size}")
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]

        holder.tvQuestion.text = question.question
        val options = question.options.shuffled() // Embaralhar opções
        holder.btnOption1.text = options[0]
        holder.btnOption2.text = options[1]
        holder.btnOption3.text = options[2]
        holder.btnOption4.text = options[3]

        val buttons = listOf(holder.btnOption1, holder.btnOption2, holder.btnOption3, holder.btnOption4)
        for (button in buttons) {
            button.setOnClickListener {
                val isCorrect = button.text == question.answer
                onAnswerSelected(question, isCorrect)
            }
        }
    }

    fun updateQuestions(newQuestions: List<Question>) {
        questions.clear()
        questions.addAll(newQuestions)
        notifyDataSetChanged()
    }
}
