package com.example.contactlist.services;

import com.example.contactlist.entities.Contact;
import com.example.contactlist.services.mappers.ContactRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ContactRepositoryImpl implements ContactRepository {

    private final JdbcTemplate template;

    @Override
    public void save(Contact contact) {
        if (contact.getId() != null) {
            String sql = "UPDATE contacts SET first_name=?, last_name=?, email=?, phone=? WHERE id=?";
            template.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());
            return;
        }
        String sql = "INSERT INTO contacts(first_name, last_name, email, phone) VALUES (?,?,?,?)";
        template.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone());
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM contacts WHERE id=?";
        template.update(sql, id);
    }

    @Override
    public List<Contact> getContacts() {
        String sql = "SELECT * FROM contacts";
        return template.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findContactById(int id) {
        String sql = "SELECT * FROM contacts WHERE id=?";
        Contact contact = DataAccessUtils.singleResult(
                template.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                )
        );
        return Optional.ofNullable(contact);
    }
}
