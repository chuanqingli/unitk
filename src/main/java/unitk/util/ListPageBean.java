package unitk.util;

import java.util.*;
public class ListPageBean<T> implements java.io.Serializable{
	private int    _nDataCount  = -1;//记录总数
	private int    _nPageCount  = -1;//总页数
	private int    _nLastSize   = -1;//最后一页记录数
	private int    _nPageNumber = -1;//页码
	private int    _nPageSize   = -1;//每页记录数

    // public static <K,T> ListPageBean<K> clone(ListPageBean<T> pp,List<K> ll){
    //     ListPageBean<K> cc = new ListPageBean<K>();
    //     cc._nDataCount  = pp._nDataCount ;
    //     cc._nPageCount  = pp._nPageCount ;
    //     cc._nLastSize   = pp._nLastSize  ;
    //     cc._nPageNumber = pp._nPageNumber;
    //     cc._nPageSize   = pp._nPageSize  ;
    //     cc._list = ll;
    //     return cc;
    // }

    public static <K> ListPageBean<K> clone(ListPageBean<Map<String,Object>> pp,Class kcls){
        ListPageBean<K> cc = new ListPageBean<K>();
        cc._nDataCount  = pp._nDataCount ;
        cc._nPageCount  = pp._nPageCount ;
        cc._nLastSize   = pp._nLastSize  ;
        cc._nPageNumber = pp._nPageNumber;
        cc._nPageSize   = pp._nPageSize  ;
        VoMapUtil vomapUtil = new VoMapUtil();
        vomapUtil.copyMapToVo(pp._list,cc._list,kcls);
        return cc;
    }

    private List<T> _list = new ArrayList<T>();

	public int getDataCount(){
		return _nDataCount;
	}
	
	public int getPageCount(){
		return _nPageCount;
	}
	
	public int getLastSize(){
		return _nLastSize;
	}
	
	public int getPageNumber(){
		return _nPageNumber;
	}
	
	public int getPageSize(){
		return _nPageSize;
	}

    public boolean hasFirstPage(){//是否出现首页
        if(_nPageCount<=1)return false;
        if(_nPageNumber==1)return false;
        return true;
    }

    public boolean hasLastPage(){//是否出现最后一页
        if(_nPageCount<=1)return false;
        if(_nPageNumber==_nPageCount)return false;
        return true;
    }

    public boolean hasPreviousPage(){
        if(_nPageNumber>1)return true;
        return false;
    }

    public boolean hasNextPage(){
        if(_nPageCount>1&&_nPageCount>_nPageNumber)return true;
        return false;
    }

	public void setDataCount(int n){
        _nDataCount = n;
    }
	public void setPageCount(int n){
        _nPageCount = n;
    }
	public void setLastSize(int n){
        _nLastSize = n;
    }
	public void setPageNumber(int n){
        _nPageNumber = n;
    }
	public void setPageSize(int n){
        _nPageSize = n;
    }

    public void setDataList(List<T> aList){
        _list = aList;
    }

    public List<T> getDataList(){
        return _list;
    }

    public boolean isEmpty(){
        return _list==null||_list.size()<=0;
    }
    

}

