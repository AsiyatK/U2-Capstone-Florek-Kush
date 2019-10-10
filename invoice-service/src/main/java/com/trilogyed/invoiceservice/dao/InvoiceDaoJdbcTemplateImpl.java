package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.models.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //prepared statements
    private final static String INSERT_INVOICE =
            "insert into invoice (customer_id, purchase_date) values (?,?)";
    private final static String SELECT_INVOICE =
            "select * from invoice where invoice_id = ?";
    private final static String SELECT_INVOICE_BY_CUSTOMER =
            "select * from invoice where customer_id = ?";
    private final static String SELECT_ALL_INVOICES =
            "select * from invoice";
    private final static String UPDATE_INVOICE =
            "update invoice set customer_id=?, purchase_date=? where invoice_id = ?";
    private final static String DELETE_INVOICE =
            "delete from invoice where invoice_id=?";

    //db row to object mapper
    private Invoice mapRowToInvoice (ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setCustomerId(rs.getInt("customer_id"));
        invoice.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());

        return invoice;
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(
                INSERT_INVOICE,
                invoice.getCustomerId(),
                invoice.getPurchaseDate()
        );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        invoice.setInvoiceId(id);

        return invoice;
    }

    @Override
    public Invoice getInvoice(int invoiceId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVOICE, this::mapRowToInvoice, invoiceId);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Invoice> getInvoicesByCustomer(int customerId) {
        return jdbcTemplate.query(SELECT_INVOICE_BY_CUSTOMER, this::mapRowToInvoice, customerId);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return jdbcTemplate.query(SELECT_ALL_INVOICES, this::mapRowToInvoice);
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        jdbcTemplate.update(
                UPDATE_INVOICE,
                invoice.getCustomerId(),
                invoice.getPurchaseDate(),
                invoice.getInvoiceId()
        );
    }

    @Override
    public void deleteInvoice(int invoiceId) {
        jdbcTemplate.update(DELETE_INVOICE, invoiceId);
    }
}
