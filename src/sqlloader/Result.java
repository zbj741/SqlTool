package sqlloader;

import interfaces.IResult;

public class Result implements IResult{
	private int _count;
	private boolean _result;
	private String _info;
	
	public Result(int count, boolean result, String info) {
		_count = count;
		_result = result;
		_info = info==null? "":info;
	}
	
	@Override
	public int getInfluencedCount() {
		// TODO Auto-generated method stub
		return this._count;
	}

	@Override
	public boolean getIsSuccessed() {
		// TODO Auto-generated method stub
		return this._result;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return this._info;
	}
}
