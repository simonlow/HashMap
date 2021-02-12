package assignment3_f20;

public class HashMap_imp implements HashMap { 
  HMCell[] tab;
  int nelts;
  
  //-------------------------------------------------------------

  HashMap_imp (int num) { 
    this.tab = new HMCell[num];
    // for (int i=0; i<num; i++) { tab[i] = null; }
    // we can rely on the Java compiler to fill the table array with nulls
    // another way would be Array.fill()
    this.nelts = 0; 
  }

  //-------------------------------------------------------------
  
  public int hash (String key, int tabSize) {
    int hval = 7;
    for (int i=0; i<key.length(); i++) {
      hval = (hval*31) + key.charAt(i);
    }
    hval = hval % tabSize;
    if (hval<0) { hval += tabSize; }
    return hval;
  }
  

  @Override
  public HMCell[] getTable() { return this.tab; }

  //
  public Value put(String k, Value v) {
	  if (hasKey(k)) {
		  for (int i = 0; i < tab.length; i++) {
			  HMCell cell = tab[i];
			  while (cell != null) {
				   if (cell.getKey().equals(k)) {
					   Value old = cell.getValue();
					   cell.setValue(v);
					   return old;
				   }
				   cell = cell.getNext();
			  }
		  } 
		  return null; 
	  }
	  else {
		  if ((getTable().length - lambda()*getTable().length) <= 1.0)
			  extend();
		  int index = hash(k, this.getTable().length);
		  if (getTable()[index] != null) {
			  HMCell cell = this.tab[index];
			  while (cell.getNext() != null) {
				  cell = cell.getNext();
			  }
			  HMCell newCell = new HMCell_imp(k, v);
			  cell.setNext(newCell);
			  return null;
		  }
		  else {
			  HMCell cell = new HMCell_imp(k, v);
			  getTable()[index] = cell;
			  return null;
		  }
	  }
  }
  
  //passed
  public Value get(String k) {
	  if (hasKey(k)) {
		  for (int i = 0; i < tab.length; i++) {
			  HMCell cell = tab[i];
			  while (cell != null) {
				  if (cell.getKey().equals(k)) {
					  return cell.getValue();
				  }
				  cell = cell.getNext();
			  }
		  } 
	  }
	  return null;	  
  }
  
  //passed
  public void remove (String k) {
	  if (hasKey(k)) {
		  for (int i = 0; i <tab.length; i++) {
			  if (tab[i] != null) {
				  HMCell cell = tab[i];
				  if (cell.getKey().equals(k)) {
					  if (cell.getNext() != null) {
						  tab[i] = cell.getNext();
					  }
					  else {
						  tab[i] = null;
					  }
					  return;
				  }
				  while (cell.getNext() != null) {
					  if (cell.getNext().getKey().equals(k)) {
						  HMCell temp = cell.getNext().getNext();
						  cell.getNext().setNext(null);
						  cell.setNext(temp);
						  return;
					  }
					  cell = cell.getNext();
				  }
			  }
		  }
	  }
  }
  
  public boolean hasKey(String k) {
	  for (int i = 0; i < tab.length; i++) {
		  if (tab[i] != null) {
			  HMCell cell = tab[i];
			  while (cell != null) {
				  if (cell.getKey().equals(k)) {
					  return true;
				  }
				  cell = cell.getNext();
			  }
		  }
	  }
	  return false; 
  }
  
  //passed
  public int size() {
	  int s = 0;
	  
	  for (int i = 0; i < tab.length; i++) {
		  if (tab[i] != null) {
			  HMCell cell = tab[i];
			  while (cell.getNext() != null) {
				  s++;
				  cell = cell.getNext();
			  }
			  s++;
		  }
	  }
	  return s; 
  }
  
  //passed
  public String maxKey() {
	  if (this.size() == 0) {
		  return null;
	  }
	  HMCell max = null; 
	  for (int i = 0; i <tab.length; i++) {
		  HMCell cell = tab[i];
		  while (cell != null) {
			  if (max != null) {
				  if (cell.getKey().compareTo(max.getKey()) > 0) {
					  max = cell;
				  }
			  } 
			  else {
				  max = cell; 
			  }
			  cell = cell.getNext();
		  }
	  }
	  return max.getKey();
  }
  
  //passed
  public String minKey() {
	  if (this.size() == 0) {
		  return null;
	  }
	  HMCell min = null; 
	  for (int i = 0; i < tab.length; i++) {
		  HMCell cell = tab[i];
		  while (cell != null) {
			  if (min != null) {
				  if (cell.getKey().compareTo(min.getKey()) < 0) {
					  min = cell;
				  }
			  }
			  else {
				  min = cell;
			  }
			  cell = cell.getNext();
		  }
	  }
	  return min.getKey();
  }
  
  //passed
  public String[] getKeys() {
	  String [] keys = new String [this.size()];
	  int index = 0;
	  for (int i = 0; i < tab.length; i++) {
		  HMCell cell = tab[i];
		  while (cell != null) {
			  keys[index++]= cell.getKey();
			  cell = cell.getNext();
		  } 
	  }
	  return keys;
  }
  
  public double lambda() {
	  int filled = 0;
	  for (int i = 0; i < getTable().length; i++) {
		  if (getTable()[i] != null)
			  filled++;
	  }
	  return  ((double)filled)/((double)getTable().length);
  }
  public double extend() {
	  HMCell [] newTab = new HMCell[this.getTable().length * 2];
	  for (int i = 0; i < this.getTable().length; i++) {
		  if (getTable()[i] != null) {
			  HMCell cell = getTable()[i];
			  while(cell != null) {
				  newTab[this.hash(cell.getKey(), newTab.length)] = cell;
				  cell = cell.getNext();
			  }
		  }
	  }
	  this.tab = newTab;
	  return lambda();
  }
  
}

