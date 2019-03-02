package Services;

import java.sql.ResultSet;
import java.sql.Statement;

import DataConnection.NobetciDBConnection;

public class BaseService<TEntity> implements IService<TEntity> {
    private NobetciDBConnection _connection;
    protected ResultSet _resultSet;
	
    public BaseService(NobetciDBConnection connection) {
		// TODO Auto-generated constructor stub
    	_connection=connection;
	}
    
   
    
    public ResultSet getSet(Statement statement, String sql){
        try {
          return set_resultSet(statement.executeQuery(sql));
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
            return null;
    }

    public TEntity findId(int id){
        try {
            return null;
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
	@Override
	public ResultSet result(String query) {
		// TODO Auto-generated method stub
		 try {
	            return  _connection.setStatement().executeQuery(query);
	        }catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
	    return null;
	}



	@Override
	public boolean Connect() {
		// TODO Auto-generated method stub
		return false;
	}



	public ResultSet get_resultSet() {
		return _resultSet;
	}



	public ResultSet set_resultSet(ResultSet _resultSet) {
		this._resultSet = _resultSet;
		return _resultSet;
	}

}
