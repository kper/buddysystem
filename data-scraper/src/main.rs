use scraper::{Html, Selector};
use std::fs::File;
use std::io::Read;

fn main() {
    let mut content = String::new();
    let mut fs = File::open("u_find.html").expect("cannot find u_find.html file");
    fs.read_to_string(&mut content)
        .expect("cannot read into string");

    let document = Html::parse_document(&content);

    let course_selector = Selector::parse(".course").unwrap();
    let exam_selector = Selector::parse(".exam").unwrap();

    let number_selector = Selector::parse(".number").unwrap();
    let ty_selector = Selector::parse(".type").unwrap();
    let what_selector = Selector::parse(".what").unwrap();

    let mut results = Vec::new();

    for element in document.select(&course_selector) {
        let number: Vec<_> = element
            .select(&number_selector)
            .into_iter()
            .map(|w| w.inner_html())
            .collect();
        let ty: Vec<_> = element
            .select(&ty_selector)
            .into_iter()
            .map(|w| w.inner_html())
            .collect();
        let what: Vec<_> = element
            .select(&what_selector)
            .into_iter()
            .map(|w| w.inner_html())
            .collect();

        //println!("{:?} {:?} {:?}", number, ty, what);

        results.push(format!("{} {} {}", number[0], ty[0], what[0]));
    }

    let link_selector = Selector::parse(".link").unwrap();

    for element in document.select(&exam_selector) {
        let link: Vec<_> = element
            .select(&link_selector)
            .into_iter()
            .map(|w| w.inner_html())
            .collect();

        //println!("{:?}", link);

        results.push(format!("{}", link[0]));
    }

    // PRINTING SQL

    for item in results.into_iter() {
        println!("INSERT INTO course (name) VALUES ('{}');", item);
    }
}
