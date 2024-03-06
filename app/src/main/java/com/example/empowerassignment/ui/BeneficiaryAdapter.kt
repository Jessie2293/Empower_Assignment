package com.example.empowerassignment.ui


import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.res.Resources
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.empowerassignment.data.BeneficiaryData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class BeneficiaryAdapter(
    private val context: Context,
    private val beneficiaries: List<BeneficiaryData>
) :
    RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder {
        val linearLayout = LinearLayout(context)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(0, 8.dpToPx(), 0, 8.dpToPx())
        linearLayout.layoutParams = layoutParams
        linearLayout.orientation = LinearLayout.VERTICAL

        val firstNameTextView = TextView(context)
        firstNameTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.addView(firstNameTextView)

        val lastNameTextView = TextView(context)
        lastNameTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.addView(lastNameTextView)

        val designationTextView = TextView(context)
        designationTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.addView(designationTextView)

        return BeneficiaryViewHolder(
            linearLayout,
            firstNameTextView,
            lastNameTextView,
            designationTextView
        )
    }

    private fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

    override fun onBindViewHolder(holder: BeneficiaryViewHolder, position: Int) {
        val beneficiary = beneficiaries[position]
        holder.bind(beneficiary)
    }

    override fun getItemCount(): Int {
        return beneficiaries.size
    }

    inner class BeneficiaryViewHolder(
        itemView: LinearLayout,
        private val firstNameTextView: TextView,
        private val lastNameTextView: TextView,
        private val designationTextView: TextView
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val beneficiary = beneficiaries[position]
                    showAdditionalInfoDialog(beneficiary)
                }
            }
        }

        fun bind(beneficiary: BeneficiaryData) {
            firstNameTextView.text = "First Name: ${beneficiary.firstName}"
            lastNameTextView.text = "Last Name: ${beneficiary.lastName}"
            designationTextView.text = "Designation Code: ${beneficiary.designationCode}"
        }
    }

    // Setting up my alert dialog
    private fun showAdditionalInfoDialog(beneficiary: BeneficiaryData) {
        val dialogView = LinearLayout(context)
        dialogView.orientation = LinearLayout.VERTICAL

        val ssnTextView = TextView(context)
        ssnTextView.text = "SSN: ${beneficiary.socialSecurityNumber}"
        dialogView.addView(ssnTextView)

        //reformatting to proper date
        val dobTextView = TextView(context)
        val originalFormat = SimpleDateFormat("MMddyyyy", Locale.getDefault())
        val targetFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val dateOfBirth: Date = originalFormat.parse(beneficiary.dateOfBirth)
        val formattedDateOfBirth: String = targetFormat.format(dateOfBirth)
        dobTextView.text = "Date of Birth: $formattedDateOfBirth"
        dialogView.addView(dobTextView)

        val addressTextView = TextView(context)
        addressTextView.text = "Address: ${beneficiary.beneficiaryAddress.firstLineMailing} \n" +
                "${beneficiary.beneficiaryAddress.city}, " +
                "${beneficiary.beneficiaryAddress.stateCode} \n${beneficiary.beneficiaryAddress.zipCode} \n" +
                "${beneficiary.beneficiaryAddress.country}"
        dialogView.addView(addressTextView)

        val phoneNumberTextView = TextView(context)
        phoneNumberTextView.text = "Phone Number: ${beneficiary.phoneNumber}"
        dialogView.addView(phoneNumberTextView)



        AlertDialog.Builder(context)
            .setTitle("Additional Information")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}

