package ecobill.module.base.ui.article;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.NumberSequence;
import ecobill.core.system.Constants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// @todo document me!

/**
 * ArticleAction.
 * <p/>
 * User: rro
 * Date: 06.11.2005
 * Time: 10:39:22
 *
 * @author Roman R&auml;dle
 * @version $Id: ArticleAction.java,v 1.1 2005/11/06 23:32:32 raedler Exp $
 * @since EcoBill 1.0
 */
public class ArticleAction {

    private ArticleUI articleUI;

    public ArticleAction(ArticleUI articleUI) {
        this.articleUI = articleUI;
    }

    public final ActionListener DELETE_ACTION = new DeleteArticle();

    private class DeleteArticle implements ActionListener {

        /**
         * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {

            // Die selektierte Reihe in der Tabelle.
            int row = articleUI.getOverviewArticleTable().getTable().getSelectedRow();

            // Die Id des selektierten Artikels.
            Long articleId = articleUI.getOverviewArticleTable().getIdOfSelectedRow();

            // Löscht den markierten Artikel.
            articleUI.getBaseService().delete(Article.class, articleId);

            // Zeichnet die Tabelle neu.
            articleUI.getOverviewArticleTable().renewTableModel();

            try {
                articleUI.getOverviewArticleTable().getTable().setRowSelectionInterval(row - 1, row - 1);
            }
            catch (IllegalArgumentException iae) {
                articleUI.getOverviewArticleTable().getTable().setRowSelectionInterval(0, 0);
            }

            // Zeige selektierten Artikel an.
            articleUI.showArticle(articleUI.getOverviewArticleTable().getIdOfSelectedRow());
        }
    }
}
