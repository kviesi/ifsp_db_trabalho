package br.edu.ifspsaocarlos.sdm.agenda.provider;

import android.content.UriMatcher;
import android.net.Uri;


/**
 * Created by viesi on 11/12/15.
 */
public class AgendaProviderContract {

    private static final String AUTHORITY = "br.edu.ifspsaocarlos.sdm.agenda.provider";

    private static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{

        uriMatcher.addURI(AUTHORITY, Agenda.AGENDA_PATH, Agenda.OPERATION_LIST);

        uriMatcher.addURI(AUTHORITY, Agenda.AGENDA_PATH + "/#", Agenda.OPERATION_GET_BY_ID);

        uriMatcher.addURI(AUTHORITY, Agenda.AGENDA_PATH + "/find/*", Agenda.OPERATION_GET_BY_NAME_EMAIL);

    }

    public static final UriMatcher getMatcher() {
        return uriMatcher;
    }

    public static class Agenda {

        public static final String MEDIA_TYPE_ALL = "vnd.android.cursor.dir/vnd.br.edu.ifspsaocarlos.agenda.contatos";
        public static final String MEDIA_TYPE_ITEM= "vnd.android.cursor.item/vnd.br.edu.ifspsaocarlos.agenda.contatos";

        public static final int OPERATION_LIST = 1;
        public static final int OPERATION_GET_BY_ID = 2;
        public static final int OPERATION_GET_BY_NAME_EMAIL = 3;

        private static final String AGENDA_PATH = "agenda";

        /**
         * Listagem
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, AGENDA_PATH);

        /**
         * Busca por ID
         */
        public static final Uri CONTENT_URI_BY_(long id) {
            return Uri.withAppendedPath(CONTENT_URI, "/" + id);
        }

        /**
         * Busca por email ou nome.
         */
        public static final Uri CONTENT_URI_QUERY_(String query) {
            return Uri.withAppendedPath(CONTENT_URI, "/find/" + query);
        }

        /**
         * Fields
         */
        public static final String KEY_ID = SQLiteHelper.KEY_ID;

        public static final String KEY_NAME = SQLiteHelper.KEY_NAME;

        public static final String KEY_FONE = SQLiteHelper.KEY_FONE;

        public static final String KEY_CELULAR = SQLiteHelper.KEY_CELULAR;

        public static final String KEY_EMAIL = SQLiteHelper.KEY_EMAIL;

        public static final String KEY_ANIVERSARIO = SQLiteHelper.KEY_ANIVERSARIO;

    }
}
