package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.chat.ChatRoomMember;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomMemberCrudRepository extends CrudRepository<ChatRoomMember, Long> {

}
