def split_words(data)
  result = []
  data.scan(/[a-z]{2,}/).reject {
    |w| $stop_words.include? w}.each {
      |w| result.push([w, 1]) }
  return result
end

def count_words(sack, item)
  map = Hash.new(0)
  sack.each{|x| map[x[0]] += x[1]} # don't need to 'normalize'  by insert(0, [])
  item.each{|x| map[x[0]] += x[1]}
  return map.to_a
end

$stop_words = File.read('../stop_words.txt').split(',')
$data_str = File.read(ARGV[0]).downcase
$nlines = 200

partition = Enumerator.new do |p|
  lines = $data_str.split("\n")
  (0..lines.size-1).step($nlines).each {
    |x| p.yield lines[x..x+$nlines-1].join("\n") }
end

splits = partition.map(&method(:split_words))
word_freqs = splits.reduce(&method(:count_words))
word_freqs.sort_by{|x,y|-y}[0..24].each { |w,f| puts "#{w} - #{f}" }
