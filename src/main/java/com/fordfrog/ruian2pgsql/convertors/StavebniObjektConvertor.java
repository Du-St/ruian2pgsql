/**
 * Copyright 2012 Miroslav Šulc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.fordfrog.ruian2pgsql.convertors;

import com.fordfrog.ruian2pgsql.containers.StavebniObjekt;
import com.fordfrog.ruian2pgsql.utils.Namespaces;
import com.fordfrog.ruian2pgsql.utils.PreparedStatementEx;
import com.fordfrog.ruian2pgsql.utils.Utils;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Convertor for StavebniObjekt element.
 *
 * @author fordfrog
 */
public class StavebniObjektConvertor
        extends AbstractSaveConvertor<StavebniObjekt> {

    /**
     * Namespace of the element.
     */
    private static final String NAMESPACE = Namespaces.STAV_OBJ_INT_TYPY;
    /**
     * SQL statement for checking whether the item already exists.
     */
    private static final String SQL_EXISTS =
            "SELECT 1 FROM rn_stavebni_objekt WHERE kod = ?";
    /**
     * SQL statement for insertion of new item.
     */
    private static final String SQL_INSERT = "INSERT INTO rn_stavebni_objekt "
            + "(nespravny, identifikacni_parcela_id, momc_kod, cobce_kod, "
            + "budova_id, cisla_domovni, dokonceni, je_vytah_kod, "
            + "zmena_grafiky, druh_konstrukce_kod, zmena_detailu, "
            + "obestaveny_prostor, pocet_bytu, pocet_podlazi, "
            + "podlahova_plocha, pripoj_el_energie, pripoj_kanal_sit_kod, "
            + "pripoj_plyn_kod, pripoj_vodovod_kod, typ_kod, zastavena_plocha, "
            + "zpusob_vytapeni_kod, zpusob_vyuziti_kod, id_trans_ruian, "
            + "plati_od, nz_id_globalni, definicni_bod, hranice, kod) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
            + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    /**
     * SQL statement for update of existing item.
     */
    private static final String SQL_UPDATE = "UPDATE rn_stavebni_objekt "
            + "SET nespravny = ?, identifikacni_parcela_id = ?, momc_kod = ?, "
            + "cobce_kod = ?, budova_id = ?, cisla_domovni = ?, dokonceni = ?, "
            + "je_vytah_kod = ?, zmena_grafiky = ?, druh_konstrukce_kod = ?, "
            + "zmena_detailu = ?, obestaveny_prostor = ?, pocet_bytu = ?, "
            + "pocet_podlazi = ?, podlahova_plocha = ?, pripoj_el_energie = ?, "
            + "pripoj_kanal_sit_kod = ?, pripoj_plyn_kod = ?, "
            + "pripoj_vodovod_kod = ?, typ_kod = ?, zastavena_plocha = ?, "
            + "zpusob_vytapeni_kod = ?, zpusob_vyuziti_kod = ?, "
            + "id_trans_ruian = ?, plati_od = ?, nz_id_globalni = ?, "
            + "definicni_bod = ?, hranice = ? WHERE kod = ? AND plati_od < ?";

    /**
     * Creates new instance of StavebniObjektConvertor.
     */
    public StavebniObjektConvertor() {
        super(StavebniObjekt.class, Namespaces.VYMENNY_FORMAT_TYPY,
                "StavebniObjekt", SQL_EXISTS, SQL_INSERT, SQL_UPDATE);
    }

    @Override
    protected void fill(final PreparedStatement pstm, final StavebniObjekt item,
            final boolean update) throws SQLException {
        final PreparedStatementEx pstmEx = new PreparedStatementEx(pstm);
        pstm.setBoolean(1, item.isNespravny());
        pstmEx.setLong(2, item.getIdentifikacniParcelaId());
        pstmEx.setInt(3, item.getMomcKod());
        pstmEx.setInt(4, item.getCobceKod());
        pstmEx.setLong(5, item.getBudovaId());
        pstmEx.setIntArray(6, item.getCislaDomovni());
        pstmEx.setDate(7, item.getDokonceni());
        pstmEx.setInt(8, item.getJeVytahKod());
        pstmEx.setBoolean(9, item.getZmenaGrafiky());
        pstmEx.setInt(10, item.getDruhKonstrukceKod());
        pstmEx.setBoolean(11, item.getZmenaDetailu());
        pstmEx.setInt(12, item.getObestavenyProstor());
        pstmEx.setInt(13, item.getPocetBytu());
        pstmEx.setInt(14, item.getPocetPodlazi());
        pstmEx.setInt(15, item.getPodlahovaPlocha());
        pstmEx.setBoolean(16, item.getPripojElEnergie());
        pstmEx.setInt(17, item.getPripojKanalSitKod());
        pstmEx.setInt(18, item.getPripojPlynKod());
        pstmEx.setInt(19, item.getPripojVodovodKod());
        pstmEx.setInt(20, item.getTypKod());
        pstmEx.setInt(21, item.getZastavenaPlocha());
        pstmEx.setInt(22, item.getZpusobVytapeniKod());
        pstmEx.setInt(23, item.getZpusobVyuzitiKod());
        pstm.setLong(24, item.getIdTransRuian());
        pstmEx.setDate(25, item.getPlatiOd());
        pstm.setLong(26, item.getNzIdGlobalni());
        pstm.setObject(27, item.getDefinicniBod());
        pstm.setObject(28, item.getHranice());
        pstm.setInt(29, item.getKod());

        if (update) {
            pstmEx.setDate(30, item.getPlatiOd());
        }
    }

    @Override
    protected void fillExists(final PreparedStatement pstm,
            final StavebniObjekt item) throws SQLException {
        pstm.setInt(1, item.getKod());
    }

    @Override
    protected void processElement(final XMLStreamReader reader,
            final Connection con, final StavebniObjekt item,
            final Writer logFile) throws IOException, XMLStreamException,
            SQLException {
        switch (reader.getNamespaceURI()) {
            case NAMESPACE:
                switch (reader.getLocalName()) {
                    case "CastObce":
                        item.setCobceKod(Utils.getCastObceKod(
                                reader, NAMESPACE, logFile));
                        break;
                    case "CislaDomovni":
                        processCislaDomovni(reader, item, logFile);
                        break;
                    case "DetailniTEA":
                        new CollectionConvertor(Namespaces.STAV_OBJ_INT_TYPY,
                                "DetailniTEA", Namespaces.STAV_OBJ_INT_TYPY,
                                "DetailniTEA",
                                new DetailniTEAConvertor(item.getKod())).
                                convert(reader, con, logFile);
                        break;
                    case "Dokonceni":
                        item.setDokonceni(
                                Utils.parseTimestamp(reader.getElementText()));
                        break;
                    case "DruhKonstrukceKod":
                        item.setDruhKonstrukceKod(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "Geometrie":
                        Utils.processGeometrie(
                                reader, item, NAMESPACE, logFile);
                        break;
                    case "GlobalniIdNavrhuZmeny":
                        item.setNzIdGlobalni(
                                Long.parseLong(reader.getElementText()));
                        break;
                    case "IdentifikacniParcela":
                        item.setIdentifikacniParcelaId(
                                Utils.getIdentifikacniParcelaId(
                                reader, NAMESPACE, logFile));
                        break;
                    case "IdTransakce":
                        item.setIdTransRuian(
                                Long.parseLong(reader.getElementText()));
                        break;
                    case "IsknBudovaId":
                        item.setBudovaId(
                                Long.parseLong(reader.getElementText()));
                        break;
                    case "Kod":
                        item.setKod(
                                Integer.parseInt(reader.getElementText()));
                        deleteDetailniTEA(con, item.getKod());
                        deleteZpusobyOchranyObjektu(con, item.getKod());
                        break;
                    case "Momc":
                        item.setMomcKod(
                                Utils.getMomcKod(reader, NAMESPACE, logFile));
                        break;
                    case "Nespravny":
                        item.setNespravny(
                                Boolean.valueOf(reader.getElementText()));
                        break;
                    case "ObestavenyProstor":
                        item.setObestavenyProstor(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "PlatiOd":
                        item.setPlatiOd(
                                Utils.parseTimestamp(reader.getElementText()));
                        break;
                    case "PocetBytu":
                        item.setPocetBytu(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "PocetPodlazi":
                        item.setPocetPodlazi(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "PodlahovaPlocha":
                        item.setPodlahovaPlocha(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "PripojeniKanalizaceKod":
                        item.setPripojKanalSitKod(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "PripojeniPlynKod":
                        item.setPripojPlynKod(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "PripojeniVodovodKod":
                        item.setPripojVodovodKod(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "TypStavebnihoObjektuKod":
                        item.setTypKod(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "VybaveniVytahemKod":
                        item.setJeVytahKod(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "ZastavenaPlocha":
                        item.setZastavenaPlocha(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "ZpusobVytapeniKod":
                        item.setZpusobVytapeniKod(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "ZpusobVyuzitiKod":
                        item.setZpusobVyuzitiKod(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    case "ZpusobyOchrany":
                        new CollectionConvertor(Namespaces.STAV_OBJ_INT_TYPY,
                                "ZpusobyOchrany", Namespaces.COMMON_TYPY,
                                "ZpusobOchrany",
                                new ZpusobOchranyObjektuConvertor(item.getKod())).
                                convert(reader, con, logFile);
                        break;
                    default:
                        Utils.printWarningIgnoringElement(logFile, reader);
                }

                break;
            default:
                Utils.printWarningIgnoringElement(logFile, reader);
        }
    }

    /**
     * Processes CislaDomovni element.
     *
     * @param reader  XML stream reader
     * @param item    item
     * @param logFile log file writer
     *
     * @throws XMLStreamException Thrown if problem occurred while reading XML
     *                            stream.
     * @throws IOException        Thrown if I/O problem occurred.
     */
    private void processCislaDomovni(final XMLStreamReader reader,
            final StavebniObjekt item, final Writer logFile)
            throws XMLStreamException, IOException {
        while (reader.hasNext()) {
            final int event = reader.next();

            switch (event) {
                case XMLStreamReader.START_ELEMENT:
                    processCislaDomovniElement(reader, item, logFile);
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (Utils.isEndElement(NAMESPACE, "CislaDomovni", reader)) {
                        return;
                    }
            }
        }
    }

    /**
     * Processes sub-elements of CislaDomovni element.
     *
     * @param reader  XML stream reader
     * @param item    item
     * @param logFile log file writer
     *
     * @throws XMLStreamException Thrown if problem occurred while reading XML
     *                            stream.
     * @throws IOException        Thrown if I/O problem occurred.
     */
    private void processCislaDomovniElement(final XMLStreamReader reader,
            final StavebniObjekt item, final Writer logFile)
            throws IOException, XMLStreamException {
        switch (reader.getNamespaceURI()) {
            case Namespaces.COMMON_TYPY:
                switch (reader.getLocalName()) {
                    case "CisloDomovni":
                        item.addCisloDomovni(
                                Integer.parseInt(reader.getElementText()));
                        break;
                    default:
                        Utils.printWarningIgnoringElement(logFile, reader);
                }
                break;
            default:
                Utils.printWarningIgnoringElement(logFile, reader);
        }
    }

    /**
     * Deletes DetailniTEA that belong to this StavebniObjekt.
     *
     * @param con              database connection
     * @param stavebniObjektId StavebniObjekt id
     *
     * @throws SQLException Thrown if problem occurred while deleting the items.
     */
    private void deleteDetailniTEA(final Connection con,
            final Integer stavebniObjektId) throws SQLException {
        try (final PreparedStatement pstm = con.prepareStatement(
                        "DELETE FROM rn_detailni_tea WHERE stavobj_kod = ?")) {
            pstm.setInt(1, stavebniObjektId);
            pstm.execute();
        }
    }

    /**
     * Deletes ZpusobyOchranyObjektu that belong to this StavebniObjekt.
     *
     * @param con              database connection
     * @param stavebniObjektId StavebniObjekt id
     *
     * @throws SQLException Thrown if problem occurred while deleting the items.
     */
    private void deleteZpusobyOchranyObjektu(final Connection con,
            final Integer stavebniObjektId) throws SQLException {
        try (final PreparedStatement pstm = con.prepareStatement(
                        "DELETE FROM rn_zpusob_ochrany_objektu "
                        + "WHERE stavobj_kod = ?")) {
            pstm.setInt(1, stavebniObjektId);
            pstm.execute();
        }
    }
}