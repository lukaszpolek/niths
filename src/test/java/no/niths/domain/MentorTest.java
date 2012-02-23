package no.niths.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MentorTest {
    private static final Long ID = 1L;
    private static final Integer GROUP_ID = 1;

    private static final Logger logger = LoggerFactory
                    .getLogger(MentorTest.class);

    @Test
    public void testShouldGenerateNewMentor() {
        Mentor mentor = new Mentor();
        mentor.setId(ID);
        mentor.setGroupId(GROUP_ID);

        assertThat(ID, is(equalTo(mentor.getId())));
        assertThat(GROUP_ID, is(equalTo(mentor.getGroupId())));
    }
}
