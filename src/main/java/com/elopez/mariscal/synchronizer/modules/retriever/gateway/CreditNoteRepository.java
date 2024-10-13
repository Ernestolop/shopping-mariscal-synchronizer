package com.elopez.mariscal.synchronizer.modules.retriever.gateway;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditNoteRepository extends JpaRepository<CreditNote, Long> {
  
    @Query(value = "SELECT o.\"DocEntry\", o.\"DocDate\", o.\"DocCur\", "
            + "o.\"DocRate\", o.\"CardName\", o.\"LicTradNum\", o.\"DocTotal\", "
            + "o.\"DocTotalFC\", o.\"VatSum\", o.\"VatSumFC\", o.\"GrosProfit\", "
            + "o.\"GrosProfFC\", o.\"CANCELED\", o.\"DocStatus\", o.\"DocType\", "
            + "o.\"U_EXX_FE_ClaAcc\" FROM \"ORIN\" o WHERE o.\"U_EXX_FE_ClaAcc\" LIKE '%' || :initialCreditNoteNumber || '%' "
            + "AND o.\"DocStatus\" = 'C' AND o.\"DocType\" = 'I' AND o.\"CANCELED\" = 'N' "
            + "AND o.\"DocEntry\" = '10572' " //TODO: para pruebas, sacar
            + "AND o.\"DocDate\" BETWEEN ADD_DAYS(CURRENT_TIMESTAMP, -7) AND CURRENT_TIMESTAMP ORDER BY o.\"DocTotal\" desc", nativeQuery = true)
    List<Object[]> findNewCreditNotes(@Param("initialCreditNoteNumber") String initialCreditNoteNumber);

    @Query(value = "SELECT o.\"DocEntry\", o.\"DocDate\", o.\"DocCur\", "
            + "o.\"DocRate\", o.\"CardName\", o.\"LicTradNum\", o.\"DocTotal\", "
            + "o.\"DocTotalFC\", o.\"VatSum\", o.\"VatSumFC\", o.\"GrosProfit\", "
            + "o.\"GrosProfFC\", o.\"CANCELED\", o.\"DocStatus\", o.\"DocType\", "
            + "o.\"U_EXX_FE_ClaAcc\", o.\"UpdateDate\" FROM \"ORIN\" o WHERE o.\"U_EXX_FE_ClaAcc\" LIKE '%' || :initialCreditNoteNumber || '%' "
            + "AND o.\"DocStatus\" = 'C' AND o.\"DocType\" = 'I' AND o.\"CANCELED\" = 'Y' "
                + "AND o.\"DocEntry\" = '10572' " //TODO: para pruebas, sacar
            + "AND o.\"DocDate\" BETWEEN ADD_DAYS(CURRENT_TIMESTAMP, -7) AND CURRENT_TIMESTAMP ORDER BY o.\"DocTotal\" desc", nativeQuery = true)
    List<Object[]> findCancelledCreditNotes(@Param("initialCreditNoteNumber") String initialCreditNoteNumber);

}
