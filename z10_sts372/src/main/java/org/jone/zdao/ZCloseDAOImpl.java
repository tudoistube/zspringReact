package org.jone.zdao;
//...과제24-2.H. /z09_sts372/src/main/java/org/jone/zdao/CloseDAOImpl.java 생성.
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.jone.zdomain.Close009VO;
import org.jone.zdomain.EmpVO;
import org.springframework.stereotype.Repository;
/*
 * @Repository는 DAO를 스프링에 인식시키기 위해서 주로 사용함.
 * root-context.xml에서 context:component-scan base-package의 속성값이 정확해야
 * root-context.xml의 BeansGraph에 BoardDaoImpl이 나타남.
 * http://www.mybatis.org/mybatis-3/ko/java-api.html
 * SqlSessions 참조.
    <T> T selectOne(String statement, Object parameter)
	<E> List<E> selectList(String statement, Object parameter)
	<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey)
	int insert(String statement, Object parameter)
	int update(String statement, Object parameter)
	int delete(String statement, Object parameter)
 */
@Repository
public class ZCloseDAOImpl implements IF_ZCloseDAO {
	//...root-context.xml의 org.mybatis.spring.SqlSessionTemplate을 주입받아 사용함.
	@Inject
	private SqlSession session;
	
	//.../zex01/src/main/resources/mappers/boardMapper.xml에서 설정한 namespace 참조.	
	private static String namespace = "org.jone.mapper.CloseMapper";
		
	
	@Override
	public List<Close009VO> listAll_close009() throws Exception {
		return session.selectList(namespace + ".listAll_close009");
	}

}
