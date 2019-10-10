package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.models.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceItemDaoJdbcTemplateImpl implements InvoiceItemDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceItemDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    //prepared statements
    private final static String INSERT_INVOICE_ITEM =
            "insert into invoice_item (invoice_id, inventory_id, quantity, unit_price) values (?,?,?, ?)";
    private final static String SELECT_INVOICE_ITEM =
            "select * from invoice_item where invoice_item_id = ?";
    private final static String SELECT_INVOICE_ITEM_BY_INVOICE =
            "select * from invoice_item where invoice_id = ?";
    private final static String SELECT_ALL_INVOICE_ITEMS =
            "select * from invoice_item";
    private final static String UPDATE_INVOICE_ITEM =
            "update invoice_item set invoice_id=?, inventory_id=?, quantity=?, unit_price=? where invoice_item_id = ?";
    private final static String DELETE_INVOICE_ITEM =
            "delete from invoice_item where invoice_item_id=?";
    private final static String DELETE_INVOICE_ITEMS_BY_INVOICE =
            "delete from invoice_item where invoice_id = ?";

    //db row to object mapper
    private InvoiceItem mapRowToInvoiceItem (ResultSet rs, int rowNum) throws SQLException {
        InvoiceItem item = new InvoiceItem();
        item.setInvoiceItemId(rs.getInt("invoice_item_id"));
        item.setInvoiceId(rs.getInt("invoice_id"));
        item.setInventoryId(rs.getInt("inventory_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setUnitPrice(rs.getBigDecimal("unit_price"));

        return item;
    }

    @Override
    public InvoiceItem addInvoiceItem(InvoiceItem item) {
        jdbcTemplate.update(
                INSERT_INVOICE_ITEM,
                item.getInvoiceId(),
                item.getInventoryId(),
                item.getQuantity(),
                item.getUnitPrice()
        );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        item.setInvoiceItemId(id);

        return item;

    }

    @Override
    public InvoiceItem getInvoiceItem(int invoiceItemId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVOICE_ITEM, this::mapRowToInvoiceItem, invoiceItemId);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<InvoiceItem> getInvoiceItemsByInvoice(int invoiceId) {
        return jdbcTemplate.query(SELECT_INVOICE_ITEM_BY_INVOICE, this::mapRowToInvoiceItem, invoiceId);

    }

    @Override
    public List<InvoiceItem> getAllInvoiceItems() {
        return jdbcTemplate.query(SELECT_ALL_INVOICE_ITEMS, this::mapRowToInvoiceItem);
    }

    @Override
    public void updateInvoiceItem(InvoiceItem item) {
        jdbcTemplate.update(
                UPDATE_INVOICE_ITEM,
                item.getInvoiceId(),
                item.getInventoryId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getInvoiceItemId()
        );
    }

    @Override
    public void deleteInvoiceItem(int invoiceItemId) {
        jdbcTemplate.update(DELETE_INVOICE_ITEM, invoiceItemId);
    }

    @Override
    public void deleteInvoiceItemsByInvoice(int invoiceId) {
        jdbcTemplate.update(DELETE_INVOICE_ITEMS_BY_INVOICE, invoiceId);
    }
}
