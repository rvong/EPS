fq = Hash.new(0)
s = File.read('../stop_words.txt').split(',')
File.read(ARGV[0]).downcase.scan(/[a-z]{2,}/).reject{|w| s.include? w}.each{|w| fq[w]+=1}
fq.sort_by{|x,y|-y}.take(25).each{|w,f| puts w+' - '+f.to_s} 
