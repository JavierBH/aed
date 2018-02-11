package aed.cache;

import es.upm.aedlib.Position;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.*;

public class Cache<Key, Value> {
	private int maxCacheSize;
	private Storage<Key, Value> storage;
	private Map<Key, CacheCell<Key, Value>> map;
	private PositionList<Key> lru;

	public Cache(int maxCacheSize, Storage<Key, Value> storage) {
		this.storage = storage;
		this.map = new HashTableMap<Key, CacheCell<Key, Value>>();
		this.lru = new NodePositionList<Key>();
		this.maxCacheSize = maxCacheSize;
	}

	public Value get(Key key) {
		Value res;
		if (map.get(key) != null) {
			lru.addFirst(map.get(key).getPos().element());
			lru.remove(map.get(key).getPos());
			map.get(key).setPos(lru.first());
			res = map.get(key).getValue();
		} else {
			res = storage.read(key);
	//		res.hashCode();
			lru.addFirst(key);
			CacheCell<Key, Value> aux = new CacheCell<Key, Value>(res, false, lru.first());
			map.put(key, aux);
			if (map.size() > maxCacheSize) {
				if (map.get(lru.last().element()).getDirty())
					storage.write(lru.last().element(), map.get(lru.last().element()).getValue());
				map.remove(lru.last().element());
				lru.remove(lru.last());
			}
		}
		return res;
	}

	public void put(Key key, Value value) {
		if (map.get(key) != null) {
			lru.addFirst(map.get(key).getPos().element());
			lru.remove(map.get(key).getPos());
			map.get(key).setPos(lru.first());
		} else {
			lru.addFirst(key);
			CacheCell<Key, Value> aux = new CacheCell<Key, Value>(value, false, lru.first());
			map.put(key, aux);
			if (map.size() > maxCacheSize) {
				if (map.get(lru.last().element()).getDirty())
					storage.write(lru.last().element(), map.get(lru.last().element()).getValue());
				map.remove(lru.last().element());
				lru.remove(lru.last());
			}
		}
		map.get(key).setValue(value);
		map.get(key).setDirty(true);
	}

}