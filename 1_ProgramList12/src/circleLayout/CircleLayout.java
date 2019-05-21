//I 12-13: 538 (555 / 728)
//程序清单12-13 简单实现了CircleLayout管理器的代码，在父组件中沿着圆形排列组件。
//这个管理器很有趣，但是没有什么实际的应用价值。示例程序的框架类见程序清单12-14.
package circleLayout;

import java.awt.*;

public class CircleLayout implements LayoutManager {
    private int minWidth = 0;
    private int minHeight = 0;
    private int preferredWidth = 0;
    private int preferredHeight = 0;
    private boolean sizeSet = false;
    private int maxComponentWidth = 0;
    private int maxComponentHeight = 0;

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    public void setSizes(Container parent){
        if(sizeSet) return;
        int n = parent.getComponentCount();

        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;
        maxComponentWidth = 0;
        maxComponentHeight = 0;

        //compute the maximum component widths and heights and set the preferred size to the sum of the component sizes.
        for(int i=0;i<n;i++){
            Component c = parent.getComponent(i);
            if(c.isVisible()){
                Dimension d = c.getPreferredSize();
                maxComponentWidth = Math.max(maxComponentWidth, d.width);
                maxComponentHeight = Math.max(maxComponentHeight, d.height);
                preferredWidth += d.width;
                preferredHeight += d.height;
            }
        }
        minWidth = preferredWidth/2;
        minHeight = preferredHeight/2;
        sizeSet = true;
    }
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        setSizes(parent);
        Insets insets = parent.getInsets();
        int width = preferredWidth + insets.left +insets.right;
        int height = preferredHeight + insets.top + insets.bottom;
        return new Dimension(width, height);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        setSizes(parent);
        Insets insets = parent.getInsets();
        int width = minWidth + insets.left + insets.right;
        int height = minHeight + insets.top + insets.bottom;
        return new Dimension(width, height);
    }

    @Override
    public void layoutContainer(Container parent) {
        setSizes(parent);

        // compute center of the circle

        Insets insets = parent.getInsets();
        int containerWidth = parent.getSize().width - insets.left - insets.right;
        int containerHeight = parent.getSize().height - insets.top - insets.bottom;

        int xcenter = insets.left + containerWidth/2;
        int ycenter = insets.right + containerHeight/2;

        // compute radius of the circle

        int xradius = (containerWidth - maxComponentWidth)/2;
        int yradius = (containerHeight - maxComponentHeight)/2;
        int radius = Math.min(xradius,yradius);

        // lay out components along the circle

        int n =  parent.getComponentCount();
        for(int i = 0;i<n;i++){
            Component c = parent.getComponent(i);
            if(c.isVisible()){
                double angle = 2*Math.PI * i/n;

                // center point of component
                int x = xcenter + (int)(Math.cos(angle)*radius);
                int y = ycenter + (int)(Math.sin(angle)*radius);

                //move component so that its center is (x,y) and its size is its preferred size
                Dimension d = c.getPreferredSize();
                c.setBounds(x-d.width/2,y-d.height/2,d.width,d.height);
            }
        }
    }
}
