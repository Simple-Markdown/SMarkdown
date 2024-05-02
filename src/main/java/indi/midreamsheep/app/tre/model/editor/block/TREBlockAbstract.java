package indi.midreamsheep.app.tre.model.editor.block;

import indi.midreamsheep.app.tre.shared.render.block.TREBlockState;
import indi.midreamsheep.app.tre.shared.render.render.prebutton.TREDefaultLinePreButton;
import indi.midreamsheep.app.tre.shared.render.render.prebutton.TRELinePreButton;
import indi.midreamsheep.app.tre.tool.id.IdUtil;
import lombok.Getter;

@Getter
public abstract class TREBlockAbstract implements TREBlock{

    protected long id = IdUtil.generateId();
    protected TREBlockState lineState;

    /**
     * 从最后获取焦点
     * */
    public void focusFromLast(){
        focus();
    }
    /**
     * 从开始获取焦点
     * */
    public void focusFormStart(){
        focus();
    }

    public TREBlockAbstract(TREBlockState lineState) {
        this.lineState = lineState;
    }

    /**
     * 获取前置按钮
     */
    @Override
    public TRELinePreButton getPreButton() {
        return new TREDefaultLinePreButton();
    }
}