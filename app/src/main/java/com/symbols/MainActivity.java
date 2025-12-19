package com.symbols;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    
    private ExpandableListView listView;
    private SymbolAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        listView = new ExpandableListView(this);
        setContentView(listView);
        
        adapter = new SymbolAdapter(this, getSymbolData());
        listView.setAdapter(adapter);
        
        listView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String symbol = adapter.getChild(groupPosition, childPosition);
            copyToClipboard(symbol);
            return true;
        });
        
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
    }
    
    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("symbol", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied: " + text, Toast.LENGTH_SHORT).show();
    }
    
    private Map<String, List<String>> getSymbolData() {
        Map<String, List<String>> data = new LinkedHashMap<>();
        
        List<String> arrows = new ArrayList<>();
        arrows.add("←"); arrows.add("→"); arrows.add("↑"); arrows.add("↓");
        arrows.add("↔"); arrows.add("↕"); arrows.add("⇐"); arrows.add("⇒");
        arrows.add("⇑"); arrows.add("⇓"); arrows.add("⇔"); arrows.add("⇕");
        arrows.add("↖"); arrows.add("↗"); arrows.add("↘"); arrows.add("↙");
        arrows.add("⟵"); arrows.add("⟶"); arrows.add("⟷"); arrows.add("↦");
        arrows.add("↣"); arrows.add("↤"); arrows.add("⇀"); arrows.add("↼");
        data.put("Arrows", arrows);
        
        List<String> operators = new ArrayList<>();
        operators.add("±"); operators.add("×"); operators.add("÷"); operators.add("∓");
        operators.add("∗"); operators.add("∘"); operators.add("∙"); operators.add("√");
        operators.add("∛"); operators.add("∜"); operators.add("∑"); operators.add("∏");
        operators.add("∫"); operators.add("∬"); operators.add("∭"); operators.add("∮");
        operators.add("∂"); operators.add("∇"); operators.add("∆"); operators.add("∞");
        operators.add("⊕"); operators.add("⊗"); operators.add("⊙"); operators.add("⊖");
        data.put("Operators", operators);
        
        List<String> relations = new ArrayList<>();
        relations.add("="); relations.add("≠"); relations.add("≈"); relations.add("≡");
        relations.add("≢"); relations.add("≤"); relations.add("≥"); relations.add("<");
        relations.add(">"); relations.add("≪"); relations.add("≫"); relations.add("∝");
        relations.add("∼"); relations.add("≃"); relations.add("≅"); relations.add("≆");
        relations.add("≇"); relations.add("≉"); relations.add("≐"); relations.add("≔");
        relations.add("≕"); relations.add("⊂"); relations.add("⊃"); relations.add("⊆");
        relations.add("⊇"); relations.add("∈"); relations.add("∉"); relations.add("∋");
        data.put("Relations", relations);
        
        List<String> greekLower = new ArrayList<>();
        greekLower.add("α"); greekLower.add("β"); greekLower.add("γ"); greekLower.add("δ");
        greekLower.add("ε"); greekLower.add("ζ"); greekLower.add("η"); greekLower.add("θ");
        greekLower.add("ι"); greekLower.add("κ"); greekLower.add("λ"); greekLower.add("μ");
        greekLower.add("ν"); greekLower.add("ξ"); greekLower.add("ο"); greekLower.add("π");
        greekLower.add("ρ"); greekLower.add("σ"); greekLower.add("τ"); greekLower.add("υ");
        greekLower.add("φ"); greekLower.add("χ"); greekLower.add("ψ"); greekLower.add("ω");
        greekLower.add("ϵ"); greekLower.add("ϑ"); greekLower.add("ϕ"); greekLower.add("ϖ");
        data.put("Greek (lowercase)", greekLower);
        
        List<String> greekUpper = new ArrayList<>();
        greekUpper.add("Α"); greekUpper.add("Β"); greekUpper.add("Γ"); greekUpper.add("Δ");
        greekUpper.add("Ε"); greekUpper.add("Ζ"); greekUpper.add("Η"); greekUpper.add("Θ");
        greekUpper.add("Ι"); greekUpper.add("Κ"); greekUpper.add("Λ"); greekUpper.add("Μ");
        greekUpper.add("Ν"); greekUpper.add("Ξ"); greekUpper.add("Ο"); greekUpper.add("Π");
        greekUpper.add("Ρ"); greekUpper.add("Σ"); greekUpper.add("Τ"); greekUpper.add("Υ");
        greekUpper.add("Φ"); greekUpper.add("Χ"); greekUpper.add("Ψ"); greekUpper.add("Ω");
        data.put("Greek (uppercase)", greekUpper);
        
        List<String> letterLike = new ArrayList<>();
        letterLike.add("ℵ"); letterLike.add("ℶ"); letterLike.add("ℷ"); letterLike.add("ℸ");
        letterLike.add("ℏ"); letterLike.add("ℑ"); letterLike.add("ℜ"); letterLike.add("℘");
        letterLike.add("ℓ"); letterLike.add("ℕ"); letterLike.add("ℙ"); letterLike.add("ℚ");
        letterLike.add("ℝ"); letterLike.add("ℤ"); letterLike.add("ℂ"); letterLike.add("∀");
        letterLike.add("∃"); letterLike.add("∄"); letterLike.add("∅"); letterLike.add("∂");
        data.put("Letter-like", letterLike);
        
        List<String> logic = new ArrayList<>();
        logic.add("∧"); logic.add("∨"); logic.add("¬"); logic.add("⊤");
        logic.add("⊥"); logic.add("⊢"); logic.add("⊣"); logic.add("⊨");
        logic.add("∀"); logic.add("∃"); logic.add("∄"); logic.add("⟹");
        logic.add("⟺"); logic.add("∴"); logic.add("∵"); logic.add("⋀");
        logic.add("⋁"); logic.add("⋂"); logic.add("⋃");
        data.put("Logic", logic);
        
        List<String> misc = new ArrayList<>();
        misc.add("°"); misc.add("′"); misc.add("″"); misc.add("‴");
        misc.add("∠"); misc.add("∡"); misc.add("⊥"); misc.add("∥");
        misc.add("⌈"); misc.add("⌉"); misc.add("⌊"); misc.add("⌋");
        misc.add("〈"); misc.add("〉"); misc.add("‖"); misc.add("†");
        misc.add("‡"); misc.add("•"); misc.add("◦"); misc.add("⋯");
        misc.add("⋮"); misc.add("⋰"); misc.add("⋱"); misc.add("…");
        data.put("Miscellaneous", misc);
        
        return data;
    }
    
    private static class SymbolAdapter extends BaseExpandableListAdapter {
        private Context context;
        private List<String> groups;
        private Map<String, List<String>> children;
        
        public SymbolAdapter(Context context, Map<String, List<String>> data) {
            this.context = context;
            this.groups = new ArrayList<>(data.keySet());
            this.children = data;
        }
        
        @Override
        public int getGroupCount() {
            return groups.size();
        }
        
        @Override
        public int getChildrenCount(int groupPosition) {
            return children.get(groups.get(groupPosition)).size();
        }
        
        @Override
        public String getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }
        
        @Override
        public String getChild(int groupPosition, int childPosition) {
            return children.get(groups.get(groupPosition)).get(childPosition);
        }
        
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        
        @Override
        public boolean hasStableIds() {
            return false;
        }
        
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView textView = (TextView) convertView;
            if (textView == null) {
                textView = new TextView(context);
                textView.setPadding(60, 30, 30, 30);
                textView.setTextSize(18);
            }
            textView.setText(getGroup(groupPosition));
            textView.setTextColor(0xFF000000);
            return textView;
        }
        
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView textView = (TextView) convertView;
            if (textView == null) {
                textView = new TextView(context);
                textView.setPadding(120, 30, 30, 30);
                textView.setTextSize(28);
            }
            textView.setText(getChild(groupPosition, childPosition));
            textView.setTextColor(0xFF0000FF);
            return textView;
        }
        
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
