package com.symbols;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    
    private ListView listView;
    private CategoryAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        listView = new ListView(this);
        setContentView(listView);
        
        adapter = new CategoryAdapter(this, getSymbolData());
        listView.setAdapter(adapter);
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
    
    private class CategoryAdapter extends BaseAdapter {
        private Context context;
        private List<Category> categories;
        
        public CategoryAdapter(Context context, Map<String, List<String>> data) {
            this.context = context;
            this.categories = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : data.entrySet()) {
                categories.add(new Category(entry.getKey(), entry.getValue()));
            }
        }
        
        @Override
        public int getCount() {
            return categories.size();
        }
        
        @Override
        public Category getItem(int position) {
            return categories.get(position);
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 20, 20, 20);
            
            Category category = getItem(position);
            
            TextView title = new TextView(context);
            title.setText(category.name);
            title.setTextSize(18);
            title.setTextColor(0xFF000000);
            title.setPadding(10, 10, 10, 15);
            layout.addView(title);
            
            GridView grid = new GridView(context);
            grid.setNumColumns(GridView.AUTO_FIT);
            grid.setColumnWidth(100);
            grid.setStretchMode(GridView.STRETCH_SPACING);
            grid.setVerticalSpacing(10);
            grid.setHorizontalSpacing(10);
            grid.setPadding(10, 0, 10, 10);
            
            SymbolGridAdapter gridAdapter = new SymbolGridAdapter(context, category.symbols);
            grid.setAdapter(gridAdapter);
            
            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < gridAdapter.getCount(); i++) {
                View listItem = gridAdapter.getView(i, null, grid);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight = listItem.getMeasuredHeight();
            }
            int numColumns = 6;
            int numRows = (int) Math.ceil((double) gridAdapter.getCount() / numColumns);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
                totalHeight * numRows + 10 * (numRows - 1) + 20);
            grid.setLayoutParams(params);
            
            grid.setOnItemClickListener((parent1, view, pos, id) -> {
                String symbol = gridAdapter.getItem(pos);
                copyToClipboard(symbol);
            });
            
            layout.addView(grid);
            return layout;
        }
    }
    
    private static class Category {
        String name;
        List<String> symbols;
        
        Category(String name, List<String> symbols) {
            this.name = name;
            this.symbols = symbols;
        }
    }
    
    private static class SymbolGridAdapter extends BaseAdapter {
        private Context context;
        private List<String> symbols;
        
        public SymbolGridAdapter(Context context, List<String> symbols) {
            this.context = context;
            this.symbols = symbols;
        }
        
        @Override
        public int getCount() {
            return symbols.size();
        }
        
        @Override
        public String getItem(int position) {
            return symbols.get(position);
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) convertView;
            if (textView == null) {
                textView = new TextView(context);
                textView.setTextSize(24);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(15, 15, 15, 15);
                textView.setTextColor(0xFF0000FF);
            }
            textView.setText(getItem(position));
            return textView;
        }
    }
}
