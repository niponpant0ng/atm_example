package com.example.atm.reposistory;

import com.example.atm.model.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DispenseRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DispenseRepository dispenseRepository;

    @Test
    @Transactional
    public void shouldDecreaseCountingFollowingSpecifyCountingInSpecifyAmount() {
        testEntityManager.persistAndFlush(new Note(100, 5));

        int updated = dispenseRepository.decreaseNoteCounting(100, 2);

        testEntityManager.clear();
        assertThat(updated, is(1));
        Note note = dispenseRepository.getOne(100);
        assertThat(note.getCounting(), is(3));
    }

}