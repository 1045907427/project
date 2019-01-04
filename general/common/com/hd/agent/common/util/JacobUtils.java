package com.hd.agent.common.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * jacob转换工具
 *
 * @author zhengziyong
 */
public class JacobUtils {
    public static final int WORD_HTML = 8;
    public static final int WORD_TXT = 7;
    public static final int EXCEL_HTML = 44;

    /**
     * WORD转HTML
     * @param docfile WORD文件全路径
     * @param htmlfile 转换后HTML存放路径
     */
    public static void wordToHtml(String docfile, String htmlfile)
    {
        // 启动word
        ActiveXComponent app = new ActiveXComponent("Word.Application");
        try
        { //设置word不可见
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();
            //打开word文件
            Dispatch doc = Dispatch.invoke(
                    docs,
                    "Open",
                    Dispatch.Method,
                    new Object[] { docfile, new Variant(false),
                            new Variant(true) }, new int[1]).toDispatch();
            //作为html格式保存到临时文件
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
                    htmlfile, new Variant(WORD_HTML) }, new int[1]);
            Variant f = new Variant(false);
            Dispatch.call(doc, "Close", f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            app.invoke("Quit", new Variant[] {});
        }
    }

    /**
     * EXCEL转HTML
     * @param xlsfile EXCEL文件全路径
     * @param htmlfile 转换后HTML存放路径
     */
    public static void excelToHtml(String xlsfile, String htmlfile)
    {
        // 启动excel
        ActiveXComponent app = new ActiveXComponent("Excel.Application");
        try
        {
            //设置excel不可见
            app.setProperty("Visible", new Variant(false));
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            //打开excel文件
            Dispatch excel = Dispatch.invoke(
                    excels,
                    "Open",
                    Dispatch.Method,
                    new Object[] { xlsfile, new Variant(false),
                            new Variant(true) }, new int[1]).toDispatch();
            //作为html格式保存到临时文件
            Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[] {
                    htmlfile, new Variant(EXCEL_HTML) }, new int[1]);
            Variant f = new Variant(false);
            Dispatch.call(excel, "Close", f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            app.invoke("Quit", new Variant[] {});
        }
    }

    /**
     * PowerPoint转成HTML
     * @param pptPath PowerPoint文件全路径
     * @param htmlPath 转换后HTML存放路径
     */
    public static void pptToHtml(String pptPath, String htmlPath){
        ActiveXComponent offCom = new ActiveXComponent("PowerPoint.Application");
        try
        {
            Dispatch dispatch = offCom.getProperty("Presentations").toDispatch();
            Dispatch dispatch1 = Dispatch.call(dispatch, "Open", pptPath, new Variant(-1), new Variant(-1), new Variant(0)).toDispatch();
            Dispatch.call(dispatch1, "SaveAs", htmlPath, new Variant(12));
            Variant variant = new Variant(false);
            Dispatch.call(dispatch1, "Close");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            offCom.invoke("Quit", new Variant[0]);
        }
    }
}
