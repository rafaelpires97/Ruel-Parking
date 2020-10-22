package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R

class ContactosAdapter(var context: Context, var header : MutableList<String>, var body : MutableList<MutableList <String>>): BaseExpandableListAdapter() {



    override fun getGroup(groupPosition : Int): String {
        return header[groupPosition]
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if(convertView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_group_contacto,null)
        }
        val title = convertView?.findViewById<TextView>(R.id.contactos_title)
        title?.text = getGroup(groupPosition)
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body[groupPosition].size
    }

    override fun getChild(groupPosition: Int,childPosition: Int): String {
        return body[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if(convertView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_item_contacto,null)
        }
        val title = convertView?.findViewById<TextView>(R.id.contactos_title)
        title?.text = getChild(groupPosition,childPosition)
        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return header.size
    }
}