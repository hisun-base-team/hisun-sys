package com.hisun.saas.sys.admin.report.util;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

/**
 * Created by jamin30 on 2015/10/22.
 */
public class JasperReportPrintUtil extends javax.swing.JApplet{

    private URL url = null;

    private javax.swing.JPanel pnlMain;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnPrint;

    private JasperPrint jasperPrint = null;

    public JasperReportPrintUtil()
    {
        initComponents();
    }

    public void init()
    {
        String strUrl = getParameter("REPORT_URL");
        if(null!=strUrl)
        {
            try
            {
                url = new URL(getCodeBase(),strUrl);
            }catch (Exception e)
            {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                JOptionPane.showMessageDialog(this,stringWriter.toString());
            }

        }
        else
        {
            JOptionPane.showMessageDialog(this,"打印路径出错！");
        }
    }

    private void initComponents()
    {
        pnlMain = new javax.swing.JPanel();
        btnPrint = new javax.swing.JButton();
        btnView = new javax.swing.JButton();

        btnPrint.setText("打印");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        pnlMain.add(btnPrint);

        btnView.setText("预览");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        pnlMain.add(btnView);

        pnlMain.setSize(btnPrint.getWidth(), btnPrint.getHeight());
        getContentPane().add(pnlMain, BorderLayout.WEST);
    }

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt)
    {
//        viewReport();
    }

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt)
    {
        printReport();
    }

    public void printReport()
    {
        if(null!=url)
        {
            if(jasperPrint == null)
            {
                try
                {
                    jasperPrint = (JasperPrint) JRLoader.loadObject(url);
                }catch (Exception e)
                {

                }
            }
        }
    }
}
